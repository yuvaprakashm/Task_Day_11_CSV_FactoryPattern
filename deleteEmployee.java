package net.texala.employee.impl;

import java.util.Scanner;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
import net.texala.employee.constants.Constants;
import net.texala.employee.exceptions.EmployeeOperationException;

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
                    System.out.println(Constants.INVALID_CHOICE);
            }
        } catch (Exception e) {
            throw new EmployeeOperationException(Constants.UNEXPECTED_ERROR + e.getMessage());
        }
    }
}
