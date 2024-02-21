package net.texala.employee.impl;

import java.util.Scanner;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
import net.texala.employee.exceptions.EmployeeOperationException;
import static net.texala.employee.constants.Constants.INVALID_CHOICE;
import static net.texala.employee.constants.Constants.UNEXPECTED_ERROR;
public class DeleteEmployee implements EmployeeOperation {

    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
            int choice = manager.getDeletionChoice(scanner);
            switch (choice) {
                case 1:
                    manager.deleteEmployee(manager, scanner);
                    break;
                case 2:
                    manager.deleteAllEmployees(manager, scanner);
                    break;
                default:
                    System.out.println(INVALID_CHOICE);
            }
        } catch (Exception e) {
            throw new EmployeeOperationException(UNEXPECTED_ERROR + e.getMessage());
        }
    }
}
