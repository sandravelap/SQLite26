package dtosJSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

public class Member {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_year")
    private Integer birthYear;

    @JsonProperty("join_date")
    private LocalDate joinDate;

    @JsonProperty("leave_date")
    private LocalDate leaveDate;

    @JsonProperty("playing_position")
    private String playingPosition;

    @JsonProperty("team_category")
    private String teamCategory;

    @JsonProperty("roles")
    private List<Role> roles;

    public Member() {}

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public LocalDate getLeaveDate() { return leaveDate; }
    public void setLeaveDate(LocalDate leaveDate) { this.leaveDate = leaveDate; }

    public String getPlayingPosition() { return playingPosition; }
    public void setPlayingPosition(String playingPosition) { this.playingPosition = playingPosition; }

    public String getTeamCategory() { return teamCategory; }
    public void setTeamCategory(String teamCategory) { this.teamCategory = teamCategory; }

    public List<Role> getRoles() { return roles; }
    public void setRoles(List<Role> roles) { this.roles = roles; }
}