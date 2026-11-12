package services;

import daos.MemberDAO;
import daos.MemberRoleDAO;
import dtosJSON.HockeyClub;
import dtosJSON.Member;
import dtosJSON.Role;
import repositories.CategoryRepository;
import repositories.MemberRepository;
import repositories.MemberRoleRepository;
import repositories.PlayingPositionRepository;

import java.sql.Date;

public class MemberJsonServices {
    public StringBuilder insertJsonMembers(HockeyClub hockeyClub) {
        StringBuilder message = new StringBuilder("Message: ");
        MemberDAO newMemberDAO;
        boolean redFlag = true;
        //para recuperar los ids de playing position y category, tengo que llamar a sus repos
        PlayingPositionRepository pprepo = new PlayingPositionRepository();
        CategoryRepository caterepo = new CategoryRepository();
        //para insertar MemberDAO su repo:
        MemberRoleRepository mrrepo = new MemberRoleRepository();
        MemberRepository memberRepository =  new MemberRepository();
        //para insertar los MemberRole su repo:
        Integer positionId;
        Integer categoryId;

        for (Member newMember : hockeyClub.getMembers()){
            newMemberDAO = new MemberDAO();
            //si no hay ninguna redFlag es que puedo hacer el insert

                newMemberDAO.setFirstName(newMember.getFirstName());
                newMemberDAO.setLastName(newMember.getLastName());
                //Cambiamos de Integer a Short
                newMemberDAO.setBirthYear(Short.parseShort(String.valueOf(newMember.getBirthYear())));
                //para pasar de LocalDate a sql.Date
                if (newMember.getJoinDate()==null){
                    newMemberDAO.setJoinDate(null);
                }else {
                    //Member tiene las fechas como LocalDate y tengo que pasarlas a sql.Date
                    newMemberDAO.setJoinDate(Date.valueOf(newMember.getJoinDate()));
                }
                //de LocalDate a sql.Date
                if (newMember.getLeaveDate()==null){
                    newMemberDAO.setLeaveDate(null);
                }else {
                    newMemberDAO.setLeaveDate(Date.valueOf(newMember.getLeaveDate()));
                }
                positionId = pprepo.idPositionByName(newMember.getPlayingPosition());
                categoryId = caterepo.idCategoryByName(newMember.getTeamCategory());
                //si me devuelve 0 es que no existe esa posición o categoría,
                // la pongo a null y aviso
                if (positionId ==0){
                    newMemberDAO.setPositionId(null);
                    message.append("The position does not exist, set to null.");
                }else{
                    newMemberDAO.setPositionId(positionId);
                }
                if (categoryId ==0){
                    newMemberDAO.setCategoryId(null);
                }else{
                    newMemberDAO.setCategoryId(categoryId);
                }
                //cargamos la información básica del Member que se almacena
                //en MemberDAO
                if (memberRepository.createMember(newMemberDAO)){
                    message.append("Member " + newMember.getLastName() + " loaded successfully");
                    //variable auxiliar para guardar los roles e insertarlos con el repo:
                    MemberRoleDAO newRoleDAO;
                    Integer idMember;
                    for (Role newRole : newMember.getRoles()){
                        newRoleDAO = new MemberRoleDAO();
                        //recupero el idMember a partir del lastname del newMember
                        idMember = memberRepository.idByLastName(newMember.getLastName());
                        if (idMember == 0){
                            message.append("Role not loaded, member not found.");
                        }else{
                            //comprobamos la restricción de la base de datos, start date no puede ser null
                            if (newRole.getStartDate()==null){
                                message.append("Role without startdate wont be loaded. ");
                            }else {
                                newRoleDAO.setMemberId(idMember);
                                newRoleDAO.setRoleId(newRole.getRoleId());
                                newRoleDAO.setStartDate(Date.valueOf(newRole.getStartDate()));
                                //gestionamos enddate null asignando null en lugar de parsear, que no se puede
                                if (newRole.getEndDate()==null){
                                    newRoleDAO.setEndDate(null);
                                }else {
                                    newRoleDAO.setEndDate(Date.valueOf(newRole.getEndDate()));
                                }
                                mrrepo.createMemberRole(newRoleDAO);
                            }
                        }
                    }
                }else{
                    message.append("Something went wrong loading "+ newMember.getLastName());
                }

        }
        return message;
    }
}
