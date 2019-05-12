package ru.morou.logger;

import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

import javafx.scene.text.Text;
import ru.morou.JavaFX.controllers.MainSceneController;

/**
 * Настройка логгера для вывода данных в консоль на экране пользователя
 * @author morou
 */
public class ClientConsoleLogAppender extends RollingFileAppender {
	
	private static MainSceneController controller;
	// будет добавлена возможность отключения консоли 
	private static boolean switchOn = false;




	@Override
	protected void subAppend(LoggingEvent event) {
		super.subAppend(event);
		if (switchOn)
			controller.writeToConsole(new Text(event.getMessage().toString() + '\n'));
	}
	
	public static void setController(MainSceneController contr) {
		controller = contr;
		switchOn = true;
	}
}
