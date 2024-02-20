package net.texala.employee.impl;

import java.util.Scanner;

import net.texala.employee.constants.Constants;
import net.texala.employee.exceptions.EmployeeOperationException;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeOperation;

public class DisplayEmployee implements EmployeeOperation {
    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println(Constants.DISPLAY_OPTION_ALL);
                System.out.println(Constants.DISPLAY_OPTION_EMPID);
                System.out.println(Constants.DISPLAY_OPTION_SORTED);
                System.out.println(Constants.DISPLAY_OPTION_MEMORY); // Add option to display in memory
                System.out.print(Constants.ENTER_CHOICE);
                int displayOption = scanner.nextInt();
                scanner.nextLine();

                switch (displayOption) {
                    case 1:
                        if (manager.displayAllEmployeesInMemory().isEmpty()) {
                            System.out.println(Constants.NO_DATA_IN_VECTOR);
                        } else {
                            manager.displayAllEmployeesInMemory().forEach(System.out::println);
                        }
                        validInput = true;
                        break;
                    case 2:
                        System.out.println(Constants.ENTER_EMPID);
                        int empId = scanner.nextInt();
                        scanner.nextLine();
                        Employee employee = manager.getEmployeeByEmpId(empId);
                        if (employee == null) {
                            System.out.println(Constants.EMP_NOTFOUND);
                        } else {
                            System.out.println(employee);
                        }
                        validInput = true;
                        break;
                    case 3:
                        manager.displaySortedEmployeeRecords(scanner);
                        validInput = true;
                        break;
                    case 4:
                        manager.displayAllEmployeesWithOption(scanner); // Handle displaying in memory
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
