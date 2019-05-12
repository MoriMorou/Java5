package ru.morou.exep;


import ru.morou.queries.StandardJsonQuery;

/**
 * Кидается при попытке передачи некорректных данных
 * @author morou
 */
@SuppressWarnings("serial")
public class IllegalDataException extends Exception {
	
	public IllegalDataException(Class<?> clazz) {
		super("Illegal file type for transmitting: " + clazz);
	}
	
	public IllegalDataException(StandardJsonQuery.QueryType jsonType) {
		super("Illegal type of json query: " + jsonType);
	}
	
	public IllegalDataException(String msg) {
		super("Illegal data for this operation: " + msg);
	}
}
