package ru.morou.queries.json;

import java.util.LinkedHashMap;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.morou.queries.StandardJsonQuery;

/**
 * Ответ с подтверждением
 * @author morou
 */
public class JsonConfirm extends StandardJsonQuery {
	
	public static final String PARAM_NAME_ANSWER = "confirmation";
	public static final String PARAM_NAME_DIR_FILES = "filesInDir";

	/**
	 * конструктор в случае успешного выполнения запроса
	 * @param files результат
	 */
	@SuppressWarnings("serial")
	public JsonConfirm(Set<String> files) {
		super(QueryType.CONFIRMATION, 
			  new LinkedHashMap<String, String>() {
				{
					put(PARAM_NAME_ANSWER, "true");
				}
			  });
		this.setParamsWithSet(new LinkedHashMap<String, Set<String>>() {
			{
				put(PARAM_NAME_DIR_FILES, files);
			}
		});
	}
	
	/**
	 * конструктор на случай неудачи
	 */
	@SuppressWarnings("serial")
	public JsonConfirm() {
		super(QueryType.CONFIRMATION, 
				new LinkedHashMap<String, String>() {
			{
				put(PARAM_NAME_ANSWER, "false");
			}
		});
	}

	/**
	 * Получить результат (подтверждение)
	 */
	@JsonIgnore
	public boolean getConfirmation() {
		return this.getStandardParams().get(PARAM_NAME_ANSWER).equals("true");
	}
	
	/**
	 * получить список файлов в текущем каталоге пользователя
	 * @return список файлов
	 */
	@JsonIgnore
	public Set<String> getFiles() {
		return this.getParamsWithSet() == null ? null : this.getParamsWithSet().get(PARAM_NAME_DIR_FILES);
	}
}