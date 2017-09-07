package classes;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="meeting")
public class Meeting {

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="department")
    private String department;

    public Meeting() {
    }

    public Meeting(String name, String description, String department) {
        this.name = name;
        this.description = description;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
