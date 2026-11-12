package repositories;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayingPositionRepository {
    public Integer idPositionByName(String positionName){
        Integer positionId = 0;
        DBConnection dbConnection = new DBConnection();
        //? se usa para meter variables de código en los prepared statement del conector jdbc que estamos usando
        String selectStmt = "SElECT position_id from playing_position where name = ?";
        try (
                Connection connection = dbConnection.connect();
                PreparedStatement pStmt = connection.prepareStatement(selectStmt)
            ) {
            pStmt.setString(1,positionName);
            ResultSet rs = pStmt.executeQuery();
            //como nos devuelve un array, hay que coger el primero:
            if (rs.next()){
                positionId = rs.getInt("position_id");
            }
        } catch (SQLException e) {
            //devolviendo un 0 el servicio gestionará la excepción (que no debería producirse porque ya hemos gestionado todo
            positionId = null;
        }
        return positionId;
    }
}
