package repositories;

import daos.MemberDAO;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            stmt.setString(4, String.valueOf(newMember.getJoinDate()));
            if (newMember.getLeaveDate()!=null) {
                stmt.setString(5, String.valueOf(newMember.getLeaveDate()));
            }
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

    public ArrayList<MemberDAO> activeMembersFromYear(Integer year){
        ArrayList<MemberDAO> activeMembers = new ArrayList<>();
        //para recorrer el result set y almacenar los memberDAO
        //necesito un MemberDAO auxiliar y el arraylist inicializado
        MemberDAO auxMember;
        DBConnection con = new DBConnection();
        String dateYear = year + "-01-01";
        String sql = "SElECT * from member where join_date < ? and leave_date is null";
        try (
                Connection miCon = con.connect();
                PreparedStatement pstmt = miCon.prepareStatement(sql);
        ) {
            pstmt.setString(1, dateYear);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                auxMember = new MemberDAO();
                auxMember.setFirstName(rs.getString("first_name"));
                auxMember.setLastName(rs.getString("last_name"));
                auxMember.setBirthYear(rs.getShort("birth_year"));
                auxMember.setJoinDate(rs.getDate("join_date"));
                auxMember.setLeaveDate(rs.getDate("leave_date"));
                auxMember.setPositionId(rs.getInt("position_id"));
                auxMember.setCategoryId(rs.getInt("category_id"));
                activeMembers.add(auxMember);
            }
        } catch (SQLException e) {
            activeMembers=null;
        }
        return activeMembers;
    }
}
