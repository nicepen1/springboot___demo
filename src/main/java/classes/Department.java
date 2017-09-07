package classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="department")
public class Department {
    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="employee")
    private String employee;


    public Department() {
    }

    public Department(String name, String description, String employee) {
        this.name = name;
        this.description = description;
        this.employee = employee;
    }

    public Department(String employee) {
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

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }
}
