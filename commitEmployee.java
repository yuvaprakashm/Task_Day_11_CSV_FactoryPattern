package net.texala.employee.impl;

import java.util.Scanner;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
import net.texala.employee.constants.Constants;
import net.texala.employee.exceptions.EmployeeOperationException;

public class commitEmployee implements EmployeeOperation {
    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
            // Display all employees before committing changes
            System.out.println("Updated Employee Records:");
            manager.displayAllEmployees().forEach(System.out::println);
            
            // Commit changes after displaying the updated records
            manager.commitChanges();
            System.out.println("Changes committed successfully.");
        } catch (Exception e) {
            EmployeeOperationException.throwCommitEmployeeException(Constants.ERROR_COMMIT_EMPLOYEE + e.getMessage());
        }
    }
}
