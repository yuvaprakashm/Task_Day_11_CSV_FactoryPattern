package net.texala.employee.impl;

import java.util.Scanner;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
import net.texala.employee.constants.Constants;
import net.texala.employee.exceptions.EmployeeOperationException;

public class deleteEmployee implements EmployeeOperation {
    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
            int choice = manager.getDeletionChoice(scanner);
            if (choice == 1) {
                System.out.println(Constants.ENTER_EMPID_TO_DELETE);
                int empIdToDelete = scanner.nextInt();
                if (manager.deleteEmployeeById(empIdToDelete)) {
                    System.out.println(Constants.DELETE_SUCCESS_MESSAGE + empIdToDelete);
                } else {
                    System.out.println(Constants.EMPLOYEE_NOT_FOUND_MESSAGE + empIdToDelete);
                }
            } else if (choice == 2) {
                manager.deleteAllEmployees();
                System.out.println(Constants.DELETE_ALL_SUCCESS_MESSAGE);
            } else {
                System.out.println(Constants.INVALID_CHOICE);
            }
        } catch (Exception e) {
            throw new EmployeeOperationException(Constants.UNEXPECTED_ERROR + e.getMessage());
        }
    }
}
