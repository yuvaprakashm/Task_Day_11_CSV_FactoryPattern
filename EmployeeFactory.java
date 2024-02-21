package net.texala.employee.factory;

 
import net.texala.employee.impl.AddEmployee;
import net.texala.employee.impl.CommitEmployee;
import net.texala.employee.impl.DeleteEmployee;
import net.texala.employee.impl.DisplayEmployee;
import net.texala.employee.impl.UpdateEmployee;
import net.texala.employee.service.EmployeeOperation;

public class EmployeeFactory {
    public EmployeeOperation createEmployeeOperation(int choice) {
        switch (choice) {
            case 1:
                return new AddEmployee();
            case 2:
                return new UpdateEmployee();
            case 3:
                return new DeleteEmployee();
            case 4:
                return new DisplayEmployee();
            case 5:
                return new CommitEmployee();
            default:
                return null;
        }
    }
}
