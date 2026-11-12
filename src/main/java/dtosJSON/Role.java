package dtosJSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Role {

    @JsonProperty("role_id")
    private Integer roleId;

    @JsonProperty("role_name")
    private String roleName;

    @JsonProperty("department")
    private String department;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    public Role() {}

    public Role(Integer roleId, String roleName, String department, LocalDate startDate, LocalDate endDate) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}