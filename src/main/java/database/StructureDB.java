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
            //execute batch permite ejecutar el script del archivo cargado previamente como String
            String sqlScriptString = Files.readString(sqlScriptPath);
            statement.addBatch(sqlScriptString);
            statement.executeBatch();
            message="Script loaded";
        } catch (SQLException ex) {
            message="Error with database: " + ex.getMessage();
        } catch (IOException e) {
            message="File not found";
        }
        return message;
    }
}
