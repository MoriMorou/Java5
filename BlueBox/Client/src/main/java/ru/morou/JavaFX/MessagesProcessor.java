package ru.morou.JavaFX;

import java.io.File;
import java.io.IOException;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;


import javafx.application.Platform;
import ru.morou.BlueBox;
import ru.morou.JavaFX.components.LabelWithInfo;
import ru.morou.JavaFX.controllers.ScreenManager;
import ru.morou.exep.IllegalDataException;
import ru.morou.processors.FileTransferHelper;
import ru.morou.processors.StandardTransference;
import ru.morou.queries.StandardJsonQuery;
import ru.morou.queries.json.JsonAuth;
import ru.morou.queries.json.JsonSendFile;

/**
 * Осуществляет отправку сообщений на сервер
 * @author morou
 */
public class MessagesProcessor {
	
	private static final Logger logger = Logger.getLogger(MessagesProcessor.class);
	
	private static final MessagesProcessor PROCESSOR_INSTANCE = new MessagesProcessor();
	
	private static final ExecutorService service = Executors.newFixedThreadPool(10);
	
	private Controller currentController;
	
	private JsonAuth auth;
	private BlueBox client;
	
	private MessagesProcessor() {}

	public static MessagesProcessor getProcessor() {
		return PROCESSOR_INSTANCE;
	}
	
	/**
	 * Отправить сообщение на сервер
	 * @param data сообщение для отправки
	 */
	private void sendData(StandardTransference data) {
			try {
				client.sendData(data);
			} catch (IOException | InterruptedException e) {
				logger.error("Transference to server failed: " + e.getMessage(), e);
			}
	}
	
	 /**
     * отправка json на серер
     * @param json запрос
     * @throws Exception
     */
    public void sendTransference(StandardJsonQuery json) {
    	try {
    		if (json instanceof JsonAuth) {
    			this.auth = (JsonAuth) json;
    			this.client = BlueBox.getInstance();
    		}
    		else
    			PROCESSOR_INSTANCE.sendData(FileTransferHelper.prepareTransference(json));

    	} catch (Exception e) {
    		logger.error("Query transference failed: " + e.getMessage(), e);
    	}
    }
    
    /**
     * отправка файла на сервер
     * @param fileInfo файл
     * @param path 
     * @throws IllegalDataException
     * @throws IOException
     */
    public void sendTransference(LabelWithInfo fileInfo, String path) {
    	
    	Thread t = new Thread(() -> {
    		JsonSendFile jsonQuery = null;
    		try {
    			jsonQuery = new JsonSendFile(fileInfo.getFileName(),
    					                     fileInfo.getFileSizeBytes(),
    					                     FileTransferHelper.get32Hex(fileInfo.getFile()),    // Check sum   ???
    					                     path,
    					                     1);

    			File file = fileInfo.getFile();

    			if (file != null && file.exists() && file.length() > 0) {

    				int parts = (int)(file.length()/FileTransferHelper.BUFFER_LEN) + 1;
    				jsonQuery.setPartsAmount(parts);
    				PROCESSOR_INSTANCE.sendData(FileTransferHelper.prepareTransference(jsonQuery));


    				for (int i = 0; i < parts; i++) {
    					StandardTransference transference = FileTransferHelper.prepareTransference(file, i, jsonQuery.getFileCheckSum());
    					PROCESSOR_INSTANCE.sendData(transference);
    				} 


    			} else
    				logger.error("File for transfer is empty or doesn't exists");
    		} catch (Exception e) {
    			logger.error("File transference failed: " + e.getMessage(), e);
    		}
    	});
    	t.setDaemon(true);
    	service.submit(t);
    }
	
	/**
	 * Обработать полученный от сервера результат аутентификации
	 * @param isAuthPass пройдена ли проверка
	 * @param files список файлов в корневом каталоге пользователя
	 * @param reason 
	 * @throws InterruptedException 
	 */
	public void login(boolean isAuthPass, Set<String> files, String reason) throws InterruptedException {
		
		if (!isAuthPass) {
			logger.debug("Authentication is failed: " + reason);
			Controller.throwAlertMessage("Authorization failed", reason);
			return;
		}

//		try {
			// аутентификация пройдена, переключаем экран
			logger.debug("Authentication is passed");
			Controller.setServerFilesSet(files);
			ScreenManager.showWorkFlowScreen();
//			Controller.setLogin(user);
//			currentController = Controller.getSceneManager().changeScene(SceneManager.Scenes.MAIN_SCENE);
//
//		} catch (IOException e) {
//			logger.error("   Scene switching failed: " + e.getMessage(), e);
//		}
	}
	
	public void refreshFilesOnClient() {
		Platform.runLater(() -> {
			currentController.refresh(Controller.FilesSource.CLIENT);
		});
	}
	
	/**
	 *
	 * @param files
	 */
	public void refreshFilesOnServer(Set<String> files) {
		Controller.setServerFilesSet(files);
		Platform.runLater(() -> {
			currentController.refresh(Controller.FilesSource.SERVER);
		});
	}

	public MessagesProcessor setController(Controller controller) {
		this.currentController = controller;
		return PROCESSOR_INSTANCE;
	}
	
	public StandardTransference getAuthData() {
		StandardTransference data = null;
		
		try {
			data = FileTransferHelper.prepareTransference(auth);
		} catch (Exception e) {
			logger.debug("Authorization data transferring failed: " + e.getMessage(), e);
		}
		return data;
	}

}
