package ru.morou.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import ru.morou.AuthManager;
import ru.morou.exep.IllegalDataException;
import ru.morou.processors.FileTransferHelper;
import ru.morou.processors.FilesProcessor;
import ru.morou.processors.StandardTransference;
import ru.morou.queries.StandardJsonQuery;
import ru.morou.queries.json.JsonAuth;
import ru.morou.queries.json.JsonConfirm;
import ru.morou.queries.json.JsonCreateDir;
import ru.morou.queries.json.JsonDelete;
import ru.morou.queries.json.JsonGetFile;
import ru.morou.queries.json.JsonGetFilesList;
import ru.morou.queries.json.JsonRename;
import ru.morou.queries.json.JsonSendFile;
import ru.morou.queries.json.JsonSimpleMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * Хендлер сервера: обрабатывает входящие сообщения в соответствии с их спецификой
 * @author morou
 */
public class TransferMessageHandler extends SimpleChannelInboundHandler<StandardJsonQuery> {
	
	private AuthManager authManager = new AuthManager();
	private FilesProcessor filesProcessor = new FilesProcessor();
	
	private static final Logger logger = Logger.getLogger(TransferMessageHandler.class);
	
	private static final ExecutorService service = Executors.newFixedThreadPool(20);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, StandardJsonQuery msg) throws Exception {
		
		StandardJsonQuery.QueryType queryType = msg.getQueryType();
		
		StandardJsonQuery jsonAnswer = null;
		
		try {
			switch (queryType) {
			case REG_DATA:
			case AUTH_DATA:              // послать ответ на запрос аутентификации
				authManager.acceptAuth((JsonAuth)msg, filesProcessor, ctx.channel());
				break;
			case SEND_FILE:              // послать подтверждение получения файла
				JsonSendFile json = (JsonSendFile) msg;
				if (json.getPartsAmount() == 0)            // передача файла прошла успешно
					jsonAnswer = new JsonConfirm(filesProcessor
							.gatherFilesFromDir(Paths.get(json.getFilePath()).getParent().toString()));
				else
					jsonAnswer = new JsonConfirm();
				break;
			case CONFIRMATION: // TODO
				//				ctx.writeAndFlush(FileTransferHelper.prepareTransference(msg));
				break;
				
			case DELETE:     // удаление файла или папки
				JsonDelete jsonDel =  (JsonDelete) msg;
				try {
					Path path = Paths.get(jsonDel.getFilePath());
					String log = filesProcessor.deleteFile(path.toString());
					logger.debug(log);
					jsonAnswer = new JsonConfirm(filesProcessor.gatherFilesFromDir(path.getParent().toString()));
					
				} catch (Exception e) {
					logger.error("Removing "+jsonDel.getFilePath()+" failed: "+e.getMessage(), e);
					jsonAnswer = new JsonSimpleMessage("Removing "+jsonDel.getFilePath()+" failed.");
				}
				break;
				
			case GET_LIST:
				JsonGetFilesList jsonList =  (JsonGetFilesList) msg;
				// путь к директории в личном хранилище пользователя
				String dir = jsonList.getFilePath();
				try {
					// получаем список файлов в заданной папке
					jsonAnswer = new JsonConfirm(filesProcessor.gatherFilesFromDir(dir));
				} catch (Exception e) {
					logger.error("Gathering files from directory "+dir+" failed: "+e.getMessage(),e);
					jsonAnswer = new JsonSimpleMessage("Obtaining list of files in directory failed");
				} 
				break;
				
			case RENAME:
				JsonRename jsonRename = (JsonRename) msg;
				try {
					Path path = Paths.get(jsonRename.getFilePath());
					String newPath = path.getParent() +
							         File.separator +
							         jsonRename.getNewFileName();
					String log = filesProcessor.moveFile(jsonRename.getFilePath(), newPath);
					logger.debug(log);
					jsonAnswer = new JsonConfirm(filesProcessor.gatherFilesFromDir(path.getParent().toString()));
				} catch (Exception e) {
					logger.error("Renaming "+jsonRename.getFilePath()+" failed: "+e.getMessage(), e);
					jsonAnswer = new JsonSimpleMessage("Renaming "+jsonRename.getFilePath()+" failed.");
				} 
				break;
			case GET_FILE:
				JsonGetFile jsonGetFile = (JsonGetFile)msg;
				sendTransference(jsonGetFile.getFilePath(), jsonGetFile.getFilePathOld(), ctx);
				break;
				
			case CREATE_DIR:
				JsonCreateDir jsonCreate = (JsonCreateDir)msg;
				try {
					String log = filesProcessor.createFolder(jsonCreate.getFilePath(), jsonCreate.getNewFolderName());
					logger.debug(log);
					jsonAnswer = new JsonConfirm(filesProcessor.gatherFilesFromDir(jsonCreate.getFilePath()));
				} catch (Exception e) {
					logger.error("Creating "+jsonCreate.getNewFolderName()+" failed: "+e.getMessage(), e);
					jsonAnswer = new JsonSimpleMessage("Creating "+jsonCreate.getNewFolderName()+" failed.");
				}
				break;

			default:      // все ошибочные сообщения, которые не должны поступать на сервер
				throw new IllegalDataException (queryType);

			}
		} finally {
			if (jsonAnswer != null) {
				ctx.writeAndFlush(FileTransferHelper.prepareTransference(jsonAnswer));
				jsonAnswer = null;
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage(), cause);
		authManager.removeFromMap(ctx.channel());
		ctx.close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		authManager.removeFromMap(ctx.channel());
		System.out.println("disconnected");
	}
	
	
	/**
	 * Подготовка и отправка запрошенного файла клиенту
	 * @param pathToFile реальный путь к файлу на сервере
	 * @param path путь к файлу, как передал его клиент
	 * @param ctx
	 */
	private void sendTransference(String pathToFile, String path, ChannelHandlerContext ctx) {
		
    	File file;
    	long fileSize;
    	
		try {
			file = Paths.get(pathToFile).toFile();
			fileSize = (long) Files.getAttribute(file.toPath(), "basic:size");
		} catch (Exception e) {
			logger.error("File for transfer ("+pathToFile+") doesn't exists: "+e.getMessage(),e);
			
			try {
				ctx.writeAndFlush(FileTransferHelper
						.prepareTransference(new JsonSimpleMessage("Server error: file doesn't exists.")));
			} catch (IOException | IllegalDataException e1) {
				logger.error(e.getMessage(), e);
			}
			return;
		}
		
		Thread t = new Thread(() -> {
			JsonSendFile jsonQuery = null;
			
			try {
				jsonQuery = new JsonSendFile(file.getName(),
											fileSize,
											FileTransferHelper.get32Hex(file),
											path,     
											1);

				int parts = (int)(file.length()/FileTransferHelper.BUFFER_LEN) + 1;
				jsonQuery.setPartsAmount(parts);


				ctx.writeAndFlush(FileTransferHelper.prepareTransference(jsonQuery));

				for (int i = 0; i < parts; i++) {
					StandardTransference transference = FileTransferHelper.prepareTransference(file, i, jsonQuery.getFileCheckSum());
					ctx.writeAndFlush(transference);
				} 
			} catch (Exception e) {
				logger.error("File ("+pathToFile+") transference failed: " + e.getMessage(), e);
				
				try {
					ctx.writeAndFlush(FileTransferHelper
							.prepareTransference(new JsonSimpleMessage("Server error: file transference failed.")));
				} catch (IOException | IllegalDataException e1) {
					logger.error(e.getMessage(), e);
				}
			}
		});
		
    	t.setDaemon(true);
    	service.submit(t);
    }
	
	
}
