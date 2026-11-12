package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    //no se necesita la conexión al SGBD porque la URL es la misma para crear la base de datos
    //si el archivo no existe, lo crea.
    private static final String URL_BD = "jdbc:sqlite:src/main/resources/chruben.db";
    //private static final String USER = "postgres"; SQLite no necesita usuario
    //private static final String PASSWORD = "admin";
    public Connection connect() throws SQLException {

        Connection connection  = DriverManager.getConnection(URL_BD);

        return connection;
    }

}
