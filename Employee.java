package net.texala.employee.model;

public class Employee {
    private int EMP_ID;
    private String FIRST_NAME;
    private String LAST_NAME;
    private String DEPARTMENT;

    public Employee(int empId, String firstName, String lastName, String department) {
        this.EMP_ID = empId;
        this.FIRST_NAME = firstName;
        this.LAST_NAME = lastName;
        this.DEPARTMENT = department;
    }

    public int getEmpId() {
        return EMP_ID;
    }

    public void setEmpId(int empId) {
        this.EMP_ID = empId;
    }

    public String getFirstName() {
        return FIRST_NAME;
    }

    public void setFirstName(String firstName) {
        this.FIRST_NAME = firstName;
    }

    public String getLastName() {
        return LAST_NAME;
    }

    public void setLastName(String lastName) {
        this.LAST_NAME = lastName;
    }

    public String getDepartment() {
        return DEPARTMENT;
    }

    public void setDepartment(String department) {
        this.DEPARTMENT = department;
    }

    @Override
    public String toString() {
        return EMP_ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + DEPARTMENT;
    }

    
}
