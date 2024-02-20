package net.texala.employee.impl;

import java.util.Scanner;

import net.texala.employee.constants.Constants;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
import net.texala.employee.exceptions.EmployeeOperationException;

public class AddEmployee implements EmployeeOperation {
    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
        	System.out.println(Constants.ADD_EMPLOYEE_HEADER_TOP);
        	System.out.println(Constants.ADD_EMPLOYEE_HEADER_MIDDLE);
        	System.out.println(Constants.ADD_EMPLOYEE_HEADER_BOTTOM);


            System.out.print(Constants.ENTER_EMPID);
            int empId = manager.getValidEmpId(scanner);

            System.out.print(Constants.ENTER_FIRST_NAME);
            String firstName = scanner.nextLine();

            System.out.print(Constants.ENTER_LAST_NAME);
            String lastName = scanner.nextLine();

            String department = manager.selectDepartment(scanner);

            System.out.println();
            manager.addEmployee(empId, firstName, lastName, department);
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (Exception e) {
            EmployeeOperationException.throwAddEmployeeException(Constants.ERROR_ADD_EMPLOYEE + e.getMessage());
        }
    }
}
