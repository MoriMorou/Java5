package ru.morou.queries.json;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.morou.queries.PathContainer;
import ru.morou.queries.StandardJsonQuery;

/**
 * запрос от клиента серверу на получение файла
 * @author morou
 */
public class JsonGetFile extends StandardJsonQuery implements PathContainer {
	
	// стандартные имена значений json 
	public static final String PARAM_NAME_FILEPATH = "filePath";
	public static final String PARAM_NAME_FILEPATH_OLD = "oldfilePath";

	/**
	 * Конструктор
	 * @param filePath путь к файлу или папке на удаление
	 */
	@SuppressWarnings("serial")
	public JsonGetFile(String filePath) {
		super(StandardJsonQuery.QueryType.GET_FILE,
				new LinkedHashMap<String, String>(){
			{
				put(PARAM_NAME_FILEPATH, filePath);
			}
		});
	}

	/**
	 * Получить путь к файлу или папке
	 * @return путь
	 */
	@JsonIgnore
	@Override
	public String getFilePath() {
		return this.getStandardParams().get(PARAM_NAME_FILEPATH);
	}

	/**
	 * Задать путь к файлу
	 * @param filePath путь
	 */
	@JsonIgnore
	@Override
	public void setFilePath(String filePath) {
		this.getStandardParams().put(PARAM_NAME_FILEPATH, filePath);
	}
	
	/**
	 * Получить путь, указанный клиентом
	 * @return путь
	 */
	@JsonIgnore
	public String getFilePathOld() {
		return this.getStandardParams().get(PARAM_NAME_FILEPATH_OLD);
	}

	/**
	 * Задать путь к файлу, указанный клитентом, при обновлении пути к файлу на сервере
	 * @param filePath путь
	 */
	@JsonIgnore
	public void setFilePathOld(String filePath) {
		this.getStandardParams().put(PARAM_NAME_FILEPATH_OLD, filePath);
	}

}
