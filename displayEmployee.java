package net.texala.employee.impl;

import java.util.Comparator;
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
    	try {
    		System.out.println(Constants.DISPLAY_OPTION_ALL);
            System.out.println(Constants.DISPLAY_OPTION_EMPID);
            System.out.println(Constants.DISPLAY_OPTION_SORTED);
            System.out.print(Constants.ENTER_CHOICE);
		int displayOption = scanner.nextInt();
		scanner.nextLine();
		switch (displayOption) {
		case 1:

			Vector<Employee> allEmployees = manager.getAllEmployees();
			if (allEmployees.isEmpty()) {
				System.out.println(Constants.NO_EMPLOYEES_FOUND);
			} else {
				System.out.println(Constants.EMPLOYEE_HEADER);
				for (Employee emp : allEmployees) {
					System.out.println(emp);
				}
			}
			break;
		case 2:

			System.out.println(Constants.ENTER_EMPID);
			int empIdToDisplay = scanner.nextInt();
			scanner.nextLine();
			Vector<Employee> employeesWithSameId = manager.getEmployeesByEmpId(empIdToDisplay);
			if (employeesWithSameId.isEmpty()) {
				System.out.println(Constants.EMPLOYEE_NOT_FOUND);
			} else {
				System.out.println(Constants.ENTER_EMPID);
				for (Employee emp : employeesWithSameId) {
					System.out.println(emp);
				}
			}
			break;
		case 3:

			System.out.println(Constants.SORT_BY);
			System.out.println(Constants.SORT_OPTION_EMPID);
			System.out.println(Constants.SORT_OPTION_FIRST_NAME);
			System.out.println(Constants.SORT_OPTION_LAST_NAME);
			System.out.print(Constants.ENTER_CHOICE);
			int sortOption = scanner.nextInt();
			scanner.nextLine();
			switch (sortOption) {
			case 1:

				EmployeeManager.displaySortedEmployeeRecords(manager,
						Comparator.comparingInt(Employee::getEmpId));
				break;
			case 2:

				EmployeeManager.displaySortedEmployeeRecords(manager,
						Comparator.comparing(Employee::getFirstName));
				break;
			case 3:

				EmployeeManager.displaySortedEmployeeRecords(manager,
						Comparator.comparing(Employee::getLastName));
				break;
			default:
				System.out.println(Constants.INVALID_OPTION_SELECTED);
				break;
			}
			break;  
		default:
			System.out.println(Constants.INVALID_OPTION_SELECTED);
			break;
		}
    }catch (Exception e) {
      	 EmployeeOperationException.throwDisplayEmployeeException(Constants.ERROR_DISPLAY_EMPLOYEE + e.getMessage());
    }
  }
  }