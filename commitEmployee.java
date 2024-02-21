package net.texala.employee.impl;

import java.util.Scanner;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
 
import net.texala.employee.exceptions.EmployeeOperationException;
import static net.texala.employee.constants.Constants.COMMIT_EMPLOYEE_HEADER_MIDDLE;
import static net.texala.employee.constants.Constants.SORTED_EMPLOYEE_RECORDS_HEADER;
import static net.texala.employee.constants.Constants.ERROR_COMMIT_EMPLOYEE;
import static net.texala.employee.constants.Constants.ADD_EMPLOYEE_HEADER_TOP;
 
import static net.texala.employee.constants.Constants.ADD_EMPLOYEE_HEADER_BOTTOM;
import static net.texala.employee.constants.Constants.UP_EMP;

public class CommitEmployee implements EmployeeOperation {

    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
            manager.commitChanges();
            System.out.println(ADD_EMPLOYEE_HEADER_TOP);
            System.out.println(COMMIT_EMPLOYEE_HEADER_MIDDLE);
            System.out.println(ADD_EMPLOYEE_HEADER_BOTTOM);

            System.out.println(UP_EMP);
            System.out.println(SORTED_EMPLOYEE_RECORDS_HEADER);
            
            manager.displayAllEmployees().forEach(System.out::println);
         
            
        } catch (Exception e) {
            EmployeeOperationException.throwCommitEmployeeException(ERROR_COMMIT_EMPLOYEE + e.getMessage());
        }
    }
}
 
