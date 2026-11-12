package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StructureDB {
    public String loadScript(){
        String message;
        DBConnection connectionBD = new DBConnection();
        try(Connection conBD = connectionBD.connect()) {
            Statement statement = conBD.createStatement();
            Path sqlScriptPath = Path.of("src/main/resources/chrubenSqlite.sql");
            //execute update se traga como statement un script entero
            String sqlScriptString = Files.readString(sqlScriptPath);
            statement.executeUpdate(sqlScriptString);
            message="Script loaded";
        } catch (SQLException ex) {
            message="Error with database: " + ex.getMessage();
        } catch (IOException e) {
            message="File not found";
        }
        return message;
    }
}
