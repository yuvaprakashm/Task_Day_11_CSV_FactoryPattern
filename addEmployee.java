package net.texala.employee.impl;

import java.util.Scanner;

import net.texala.employee.constants.Constants;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
import net.texala.employee.exceptions.EmployeeOperationException;

public class addEmployee implements EmployeeOperation {
    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
            System.out.println(Constants.ENTER_EMPID);
            int empId = scanner.nextInt();
            scanner.nextLine();

            System.out.println(Constants.ENTER_FIRST_NAME);
            String firstName = scanner.nextLine();

            System.out.println(Constants.ENTER_LAST_NAME);
            String lastName = scanner.nextLine();

            System.out.println(Constants.ENTER_DEPARTMENT);
            System.out.println(Constants.DEPARTMENT_OPTIONS);
            System.out.print(Constants.SELECT_DEPARTMENT_PROMPT);
            int departmentChoice = scanner.nextInt();
            scanner.nextLine();

            String department;
            switch (departmentChoice) {
                case 1:
                    department = Constants.DEV_DEPARTMENT;
                    break;
                case 2:
                    department = Constants.PS_DEPARTMENT;
                    break;
                case 3:
                    department = Constants.QA_DEPARTMENT;
                    break;
                case 4:
                    department = Constants.ADMIN_DEPARTMENT;
                    break;
                default:
                    System.out.println(Constants.INVALID_DEPARTMENT_DEFAULTING);
                    department = Constants.UNKNOWN_DEPARTMENT;
                    break;
            }

            manager.addEmployee(empId, firstName, lastName, department);
        } catch (Exception e) {
        	
            EmployeeOperationException.throwAddEmployeeException(Constants.ERROR_ADD_EMPLOYEE + e.getMessage());
        }
    }
}
