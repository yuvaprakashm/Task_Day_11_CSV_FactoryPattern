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
            System.out.println(Constants.ENTER_EMPID);
            int empId = manager.getValidEmpId(scanner);

            System.out.println(Constants.ENTER_FIRST_NAME);
            String firstName = scanner.nextLine();

            System.out.println(Constants.ENTER_LAST_NAME);
            String lastName = scanner.nextLine();

            String department = manager.selectDepartment(scanner);

            manager.addEmployee(empId, firstName, lastName, department);
        } catch (Exception e) {
            EmployeeOperationException.throwAddEmployeeException(Constants.ERROR_ADD_EMPLOYEE + e.getMessage());
        }
    }
}

