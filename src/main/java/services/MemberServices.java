package services;

import daos.MemberDAO;
import dtosUI.MemberUI;
import repositories.CategoryRepository;
import repositories.MemberRepository;
import repositories.PlayingPositionRepository;

import java.util.ArrayList;
import java.util.Date;

public class MemberServices {
    public String insertNewMember(MemberUI newMember) {
        String message ="Feedback: \n";
        boolean redFlag = false;
        PlayingPositionRepository pprepo = new PlayingPositionRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        MemberRepository memberRepository = new MemberRepository();
        //comprobaciones antes de llamar al MemberRepository
        //join date tiene que ser fecha anterior a hoy
        Date now = new Date(); // Crea una fecha con el instante actual del sistema
        Integer categoryId = categoryRepository.idCategoryByName(newMember.getCategory());
        Integer positionId =pprepo.idPositionByName(newMember.getPosition());
        if (newMember.getJoinDate().after(now)){
            message += "Wrong date. \n";
            redFlag = true;
        }
        //position name tiene que existir y tener un id recuperable o ser null
        if (newMember.getPosition().isBlank()){
            newMember.setPosition(null);
        }else{
            //si la función devuelve 0 es que no existe esa posición en la BD
            if (positionId ==0){
                message += "Wrong position. \n";
                redFlag = true;
            } else if (positionId==null) {
                message += "DB error";
                redFlag = true;
            }
        }
        //cateory puede existir y ser recuperable o ser null
        if (newMember.getCategory().isBlank()){
            newMember.setCategory(null);
        }else{
            if (categoryId==0){
                message += "Wrong category. \n";
                redFlag = true;
            } else if (categoryId==null) {
                message += "DB error";
                redFlag = true;
            }
        }

        //si no hay ninguna redFlag es que puedo hacer el insert
        if (!redFlag){
            MemberDAO newMemberDAO = new MemberDAO();
            newMemberDAO.setFirstName(newMember.getFirstName());
            newMemberDAO.setLastName(newMember.getLastName());
            newMemberDAO.setBirthYear(Short.parseShort(String.valueOf(newMember.getBirthYear())));
            //para pasar de util.date a ms, y usar el constructor de sql.date con parámetro ms
            if (newMember.getJoinDate()==null){
                newMemberDAO.setJoinDate(null);
            }else {
                java.sql.Date joinDateSql = new java.sql.Date(newMember.getJoinDate().getTime());
                newMemberDAO.setJoinDate(joinDateSql);
            }
            //lo ponemos para que este código sea más reutilizable
            if (newMember.getLeaveDate()==null){
                newMemberDAO.setLeaveDate(null);
            }else {
                java.sql.Date leaveDateSql = new java.sql.Date(newMember.getLeaveDate().getTime());
                newMemberDAO.setLeaveDate(leaveDateSql);
            }
            newMemberDAO.setPositionId(positionId);
            newMemberDAO.setCategoryId(categoryId);
            if(memberRepository.createMember(newMemberDAO)){
                message = "Congratulations. Member inserted.";
            }
        }
        return message;
    }

    public ArrayList<MemberUI> activeMembersFromYear(Integer year) {
        MemberRepository memberRepository = new MemberRepository();
        ArrayList<MemberUI> members = new ArrayList<>();
        ArrayList<MemberDAO> membersDAO = memberRepository.activeMembersFromYear(year);
        //parseo manual de MemberDAO a MemberUI
        MemberUI memberUIAux;
        for (MemberDAO memberDAO : membersDAO){
            memberUIAux =  new MemberUI();
            memberUIAux.setFirstName(memberDAO.getFirstName());
            memberUIAux.setLastName(memberDAO.getLastName());
            memberUIAux.setJoinDate(memberDAO.getJoinDate());
        }
        return members;
    }
}
