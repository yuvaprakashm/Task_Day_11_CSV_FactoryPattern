package net.texala.employee.impl;

import java.util.Scanner;
import java.util.Vector;

import net.texala.employee.constants.Constants;
import net.texala.employee.exceptions.EmployeeOperationException;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeOperation;

public class displayEmployee implements EmployeeOperation {
	@Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println(Constants.DISPLAY_OPTION_ALL);
                System.out.println(Constants.DISPLAY_OPTION_EMPID);
                System.out.println(Constants.DISPLAY_OPTION_SORTED);
                System.out.print(Constants.ENTER_CHOICE);
                int displayOption = scanner.nextInt();
                scanner.nextLine();  

                switch (displayOption) {
                    case 1:
                    	manager.displayAllEmployees().forEach(System.out::println);
                        validInput = true;
                        break;
                    case 2:
                        System.out.println(Constants.ENTER_EMPID);
                        int empId = scanner.nextInt();
                        scanner.nextLine();   
                        Vector<Employee> employees = manager.getEmployeesByEmpId(empId);
                        if (employees.isEmpty()) {
                            System.out.println(Constants.EMP_NOTFOUND);
                        } else {
                            System.out.println(Constants.EMPID + empId + Constants.SEMI);
                            for (Employee emp : employees) {
                                System.out.println(emp); 
                            }
                        }
                        validInput = true;
                        break;

                        
                    case 3:
                    	manager.displaySortedRecordsByOption(manager, scanner);
                        validInput = true;
                        break;

                    default:
                        System.out.println(Constants.INVALID_OPTION_SELECTED);
                        break;
                }
            } catch (Exception e) {
                System.out.println(Constants.ERROR_INVALID_INPUT);
                scanner.nextLine();  
            }
        }
    }
}
