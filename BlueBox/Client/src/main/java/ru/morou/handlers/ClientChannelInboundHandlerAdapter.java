package ru.morou.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.morou.JavaFX.Controller;
import ru.morou.JavaFX.MessagesProcessor;

import ru.morou.JavaFX.controllers.ScreenManager;
import ru.morou.queries.StandardJsonQuery;
import ru.morou.queries.json.JsonConfirm;
import ru.morou.queries.json.JsonResultAuth;
import ru.morou.queries.json.JsonSendFile;
import ru.morou.queries.json.JsonSimpleMessage;

/**
 * Хендлер клиента: обрабатывает входящие сообщения от сервера
 * @author morou
 */
public class ClientChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {
	
	private MessagesProcessor processor = MessagesProcessor.getProcessor();

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		ctx.writeAndFlush(processor.getAuthData());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		StandardJsonQuery json = (StandardJsonQuery)msg;
    	switch (json.getQueryType()) {
			case AUTH_RESULT:
				JsonResultAuth jsonAuth = (JsonResultAuth)json;
				processor.login(jsonAuth.getAuthResult(), jsonAuth.getFiles(), jsonAuth.getReason());
				break;
			case SEND_FILE:
				JsonSendFile jsonSend = (JsonSendFile) msg;
				if (jsonSend.getPartsAmount() == 0)            // передача файла прошла успешно
					processor.refreshFilesOnClient();
				else
					Controller.throwAlertMessage("ERROR", "FileBox transferring failed.");

				break;
			case CONFIRMATION:
				JsonConfirm jsonConfirm = (JsonConfirm)json;
				if (jsonConfirm.getConfirmation())
					processor.refreshFilesOnServer(jsonConfirm.getFiles());
				else
					Controller.throwAlertMessage("Error", "FileBox transmitting failed");
				break;
			case MESSAGE:
				JsonSimpleMessage jsonMsg = (JsonSimpleMessage)json;
				Controller.throwAlertMessage("Alert", jsonMsg.getMessage());
				ScreenManager.showLoginScreen();
				break;
			
				
			default:       // AUTH_DATA, REG_DATA
				break;
    	}
	}
}
