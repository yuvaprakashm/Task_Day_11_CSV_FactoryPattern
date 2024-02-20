package net.texala.employee.impl;

import java.util.Scanner;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
import net.texala.employee.constants.Constants;
import net.texala.employee.exceptions.EmployeeOperationException;

public class CommitEmployee implements EmployeeOperation {

    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
            manager.commitChanges();
            System.out.println(Constants.ADD_EMPLOYEE_HEADER_TOP);
            System.out.println(Constants.COMMIT_EMPLOYEE_HEADER_MIDDLE);
            System.out.println(Constants.ADD_EMPLOYEE_HEADER_BOTTOM);

            System.out.println(Constants.UP_EMP);
            System.out.println(Constants.SORTED_EMPLOYEE_RECORDS_HEADER);
            
            manager.displayAllEmployees().forEach(System.out::println);
        } catch (Exception e) {
            EmployeeOperationException.throwCommitEmployeeException(Constants.ERROR_COMMIT_EMPLOYEE + e.getMessage());
        }
    }
}
