package ru.morou.server.Database;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;
    private String dbServer;
    private int dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;


    public void connect() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        try (Reader in = new InputStreamReader(this.getClass().getResourceAsStream("/db.properties"))) {
            Properties properties = new Properties();
            properties.load(in);
            dbServer = properties.getProperty("dbServerAddr");
            dbPort = Integer.parseInt(properties.getProperty("dbServerPort"));
            dbName = properties.getProperty("dbName");
            dbUser = properties.getProperty("dbUser");
            dbPassword = properties.getProperty("dbPassword");
            String conURL = "jdbc:mysql://" + dbServer + ":" + dbPort + "/" + dbName +
                    "?verifyServerCertificate=false&useSSL=true" +
                    "&useLegacyDatetimeCode=false" +
                    "&serverTimezone=UTC";
            connection = DriverManager.getConnection(conURL, dbUser, dbPassword);
            createTable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            System.out.println("Cjplftv");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `username` VARCHAR(20) NOT NULL,\n" +
                    "  `firstname` VARCHAR(20) NOT NULL,\n" +
                    "  `secondname` VARCHAR(20) NOT NULL,\n" +
                    "  `email` VARCHAR(20) NOT NULL,\n" +
                    "  `password` VARCHAR(10) NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC),\n" +
                    "  UNIQUE INDEX `username_UNIQUE` (`username` ASC));");
        }
    }

}
