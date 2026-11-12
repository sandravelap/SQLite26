package repositories;

import daos.MemberDAO;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepository {
    //este repositorio me permite hacer las operaciones CRUD del DAO MemberDAO en la tabla member
    public boolean createMember (MemberDAO newMember){
        boolean insertOk;
        DBConnection dbConnection =  new DBConnection();
        String sql = "INSERT INTO member ("
                + "first_name, last_name, birth_year, join_date, leave_date, position_id, category_id"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection miCon = dbConnection.connect()) {
            PreparedStatement stmt = miCon.prepareStatement(sql);
            stmt.setString(1, newMember.getFirstName());
            stmt.setString(2, newMember.getLastName());
            stmt.setShort(3, newMember.getBirthYear());
            stmt.setDate(4, newMember.getJoinDate());
            stmt.setDate(5, newMember.getLeaveDate());
            stmt.setInt(6,newMember.getPositionId());
            stmt.setInt(7, newMember.getCategoryId());
            stmt.executeUpdate();
            insertOk = true;
        } catch (SQLException e) {
            insertOk = false;
        }
        return insertOk;
    }
    public Integer idByLastName(String lastName){
        Integer idMember = 0;
        DBConnection con = new DBConnection();
        String sql = "SElECT member_id from member where last_name = ?";
        try (
                Connection miCon = con.connect();
                PreparedStatement pstmt = miCon.prepareStatement(sql);
        ) {
            pstmt.setString(1, lastName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                idMember = rs.getInt("member_id");
            }
        } catch (SQLException e) {
            idMember=null;
        }
        return idMember;
    }
}
