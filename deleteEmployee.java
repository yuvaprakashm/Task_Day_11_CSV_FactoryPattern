package net.texala.employee.impl;

import java.util.InputMismatchException;
import java.util.Scanner;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
import net.texala.employee.constants.Constants;
import net.texala.employee.exceptions.EmployeeOperationException;

public class deleteEmployee implements EmployeeOperation {
    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
            System.out.println(Constants.MENU_OPTION_DELETE_SPECIFIC);
            System.out.println(Constants.MENU_OPTION_DELETE_ALL);
            System.out.print(Constants.MENU_OPTION_ENTER_CHOICE);
            int deleteOption = scanner.nextInt();
            scanner.nextLine();

            switch (deleteOption) {
                case 1:
                    System.out.println(Constants.DELETE_EMPLOYEE_PROMPT);
                    int empIdToDelete = scanner.nextInt();
                    scanner.nextLine();
                    boolean isDeleted = manager.deleteEmployeeById(empIdToDelete);  
                    if (isDeleted) {
                        System.out.println(String.format(Constants.SUCCESS_EMPLOYEE_DELETED, empIdToDelete));
                    } else {
                        throw new EmployeeOperationException(String.format(Constants.ERROR_EMPLOYEE_NOT_FOUND, empIdToDelete));
                    }
                    break;
                case 2:
                    manager.deleteAllEmployees();
                    System.out.println(Constants.SUCCESS_ALL_EMPLOYEES_DELETED);
                    break;
                default:
                    throw new EmployeeOperationException(Constants.ERROR_INVALID_OPTION);
            }
        } catch (InputMismatchException e) {
            throw new EmployeeOperationException(Constants.INVALID_INPUT);
        } catch (Exception e) {
            throw new EmployeeOperationException(Constants.UNEXPECTED_ERROR + e.getMessage());
        }
    }
}
