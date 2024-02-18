package net.texala.employee.factory;

 
import net.texala.employee.impl.addEmployee;
import net.texala.employee.impl.commitEmployee;
import net.texala.employee.impl.deleteEmployee;
import net.texala.employee.impl.displayEmployee;
import net.texala.employee.impl.updateEmployee;
import net.texala.employee.service.EmployeeOperation;

public class EmployeeFactory {
    public EmployeeOperation createEmployeeOperation(int choice) {
        switch (choice) {
            case 1:
                return new addEmployee();
            case 2:
                return new updateEmployee();
            case 3:
                return new deleteEmployee();
            case 4:
                return new displayEmployee();
            case 5:
                return new commitEmployee();
            default:
                return null;
        }
    }
}