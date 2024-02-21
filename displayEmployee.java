package net.texala.employee.impl;

import java.util.Scanner;

import net.texala.employee.exceptions.EmployeeOperationException;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeOperation;
import static net.texala.employee.constants.Constants.ADD_EMPLOYEE_HEADER_TOP;
import static net.texala.employee.constants.Constants.ADD_EMPLOYEE_HEADER_BOTTOM;
import static net.texala.employee.constants.Constants.DISPLAY_MENU_HEADER;
import static net.texala.employee.constants.Constants.ENTER_EMPID;
import static net.texala.employee.constants.Constants.EMP_NOTFOUND;
import static net.texala.employee.constants.Constants.INVALID_OPTION_SELECTED;
import static net.texala.employee.constants.Constants.ERROR_INVALID_INPUT;
import static net.texala.employee.constants.Constants.DISPLAY_OPTION_EMPID;
import static net.texala.employee.constants.Constants.DISPLAY_OPTION_SORTED;
import static net.texala.employee.constants.Constants.DISPLAY_OPTION_MEMORY;
import static net.texala.employee.constants.Constants.ENTER_CHOICE;
 



public class DisplayEmployee implements EmployeeOperation {
    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        boolean validInput = false;
        while (!validInput) {
            try {
            	System.out.println(ADD_EMPLOYEE_HEADER_TOP);
            	System.out.println(DISPLAY_MENU_HEADER);
                System.out.println(ADD_EMPLOYEE_HEADER_BOTTOM);
                System.out.println(DISPLAY_OPTION_EMPID);
                System.out.println(DISPLAY_OPTION_SORTED);
                System.out.println(DISPLAY_OPTION_MEMORY);  
                System.out.print(ENTER_CHOICE);
                int displayOption = scanner.nextInt();
                scanner.nextLine();

                switch (displayOption) {
                    case 1:
                        System.out.println(ENTER_EMPID);
                        int empId = scanner.nextInt();
                        scanner.nextLine();
                        Employee employee = manager.getEmployeeByEmpId(empId);
                        if (employee == null) {
                            System.out.println(EMP_NOTFOUND);
                        } else {
                            System.out.println(employee);
                        }
                        validInput = true;
                        break;
                    case 2:
                        manager.displaySortedRecordsOption(manager, scanner);
                        validInput = true;
                        break;
                    case 3:
                        manager.displayAllEmployeesWithOption(scanner);  
                        validInput = true;
                        break; 
                    default:
                        System.out.println(INVALID_OPTION_SELECTED);
                        break;
                }
            } catch (Exception e) {
                System.out.println(ERROR_INVALID_INPUT);
                scanner.nextLine();
            }
        }
    }
}
