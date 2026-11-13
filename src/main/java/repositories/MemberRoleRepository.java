package repositories;

import daos.MemberRoleDAO;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberRoleRepository {
    public boolean createMemberRole(MemberRoleDAO newMemberRole) {
        boolean insertOk = false;
        DBConnection dbConnection = new DBConnection();
        String sql = "INSERT INTO member_role (member_id, role_id, start_date, end_date) VALUES (?,?,?,?)";
        try(
                Connection con = dbConnection.connect();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setInt(1, newMemberRole.getMemberId());
            pstmt.setInt(2, newMemberRole.getRoleId());
            pstmt.setString(3, String.valueOf(newMemberRole.getStartDate()));
            if (newMemberRole.getEndDate()!=null) {
                pstmt.setString(4, String.valueOf(newMemberRole.getEndDate()));
            }
            pstmt.execute();
            insertOk=true;
        } catch (SQLException e) {
            insertOk = false;
        }
        return insertOk;
    }
}
