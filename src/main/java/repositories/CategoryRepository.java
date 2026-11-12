package repositories;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRepository {
    public Integer idCategoryByName(String categoryName){
        //si devuelve 0 no hay resultados
        Integer categoryId = 0;
        DBConnection dbConnection = new DBConnection();
        //? se usa para meter variables de código en los prepared statement del conector jdbc que estamos usando
        String selectStmt = "SElECT category_id from team_category where name = ?";
        try (
                Connection connection = dbConnection.connect();
                PreparedStatement pStmt = connection.prepareStatement(selectStmt)
        ) {
            pStmt.setString(1,categoryName);
            ResultSet rs = pStmt.executeQuery();
            //como nos devuelve un array, hay que coger el primero:
            if (rs.next()){
                categoryId = rs.getInt("category_id");
            }
        } catch (SQLException e) {
            //devolviendo un null el servicio gestionará la excepción (que no debería producirse porque ya hemos gestionado todo
            categoryId = null;
        }
        return categoryId;
    }
}
