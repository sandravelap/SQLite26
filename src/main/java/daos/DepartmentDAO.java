package daos;

public class DepartmentDAO {
    private Integer departmenId;
    private String name;

    public DepartmentDAO() {
    }

    public Integer getDepartmenId() {
        return departmenId;
    }

    public void setDepartmenId(Integer departmenId) {
        this.departmenId = departmenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
