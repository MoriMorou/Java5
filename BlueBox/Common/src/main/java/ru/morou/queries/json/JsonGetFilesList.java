package ru.morou.queries.json;

import java.util.LinkedHashMap;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.morou.queries.PathContainer;
import ru.morou.queries.StandardJsonQuery;

public class JsonGetFilesList extends StandardJsonQuery implements PathContainer {

	// стандартные имена значений json 
	public static final String PARAM_NAME_DIRPATH = "pathToDir";
	
	/**
	 * Конструктор
	 * @param filePath путь к папке
	 */
	@SuppressWarnings("serial")
	public JsonGetFilesList(String filePath) {
		super(StandardJsonQuery.QueryType.GET_LIST,
			  new LinkedHashMap<String, String>(){
				{
					put(PARAM_NAME_DIRPATH, filePath);
				}
			  });
	}
	
	/**
	 * Получить путь к папке
	 * @return путь
	 */
	@JsonIgnore
	@Override
	public String getFilePath() {
		return this.getStandardParams().get(PARAM_NAME_DIRPATH);
	}
	
	/**
	 * задать путь к папке, список файлов которой надо получить
	 * @param dirPath путь к папке
	 */
	@JsonIgnore
	@Override
	public void setFilePath(String dirPath) {
		this.getStandardParams().put(PARAM_NAME_DIRPATH, dirPath);
	}
}
