package ru.morou.exep;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class UserSQLException extends SQLException{

	public UserSQLException(String msg) {
		super(msg);
	}
}
