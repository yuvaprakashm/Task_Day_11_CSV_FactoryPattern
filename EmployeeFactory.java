package net.texala.employee.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import net.texala.employee.constants.Constants;
import net.texala.employee.model.Employee;

public class EmployeeManager {

	private Vector<Employee> employee;
	private Vector<Employee> displayedEmployees;
	private Vector<Employee> deletedEmployees;

	public EmployeeManager() {
		employee = new Vector<>();
		displayedEmployees = new Vector<>();
		deletedEmployees = new Vector<>();
		loadEmployees();

	}

	private void loadEmployees() {
		File file = new File(Constants.FILE_NAME);

		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println(Constants.NEW_EMPLOYEES_FILE_CREATED + Constants.FILE_NAME);

			} catch (IOException e) {
				System.out.println(Constants.ERROR_CREATING_NEW_EMPLOYEES_FILE + e.getMessage());

			}
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			boolean headerSkipped = false;
			while ((line = reader.readLine()) != null) {
				if (!headerSkipped) {
					headerSkipped = true;
					continue;
				}
				if (line.trim().isEmpty()) {

					continue;
				}
				String[] parts = line.split(Constants.CSV_DELIMITER);
				if (parts.length == 4) {
					try {
						int empId = Integer.parseInt(parts[0].trim());
						String firstName = parts[1].trim();
						String lastName = parts[2].trim();
						String department = parts[3].trim();
						employee.add(new Employee(empId, firstName, lastName, department));
					} catch (NumberFormatException e) {

						System.out.println(Constants.INVALID_EMPLOYEE_ID_IN_LINE + line);

					}
				} else {

					System.out.println(Constants.INVALID_DATA_FORMAT_IN_LINE + line);

				}
			}
		} catch (IOException e) {
			System.out.println(Constants.ERROR_LOADING_EMPLOYEES + e.getMessage());

		}
	}

	public void saveEmployees() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(Constants.FILE_NAME))) {
			for (Employee employee : employee) {
				writer.println(employee.getEmpId() + Constants.CSV_DELIMITER + employee.getFirstName()
						+ Constants.CSV_DELIMITER + employee.getLastName() + Constants.CSV_DELIMITER
						+ employee.getDepartment());
			}
		} catch (IOException e) {
			System.out.println(Constants.ERROR_SAV_EMP + e.getMessage());
		}
	}

	public void addEmployee(int empId, String firstName, String lastName, String department) {
		if (!isValidName(firstName) && !isValidName(lastName)) {
			System.out.println(Constants.INVALID_NAME_ERROR);
			logError(Constants.INVALID_NAME_LOG + firstName + ',' + lastName);
			return;
		}
		if (!isValidName(firstName)) {
			System.out.println(Constants.INVALID_FIRST_NAME_ERROR);
			logError(Constants.INVALID_FIRST_NAME_LOG + firstName);
			return;
		}

		if (!isValidName(lastName)) {
			System.out.println(Constants.INVALID_LAST_NAME_ERROR);
			logError(Constants.INVALID_LAST_NAME_LOG + lastName);
			return;
		}

		if (isDuplicateEmpId(empId)) {
			System.out.println(Constants.DUPLICATE_EMPID_ERROR + empId + Constants.ALREADY_EXISTS);
			logError(Constants.DUPLICATE_EMPID_LOG + empId);
			return;
		}

		employee.add(new Employee(empId, firstName, lastName, department));
		System.out.println(Constants.EMP_ADD);
	}

	private boolean isValidName(String name) {
		return name.matches(Constants.NAME_REGEX);
	}

	private boolean isDuplicateEmpId(int empId) {
		for (Employee employee : employee) {
			if (employee.getEmpId() == empId) {
				return true;
			}
		}
		return false;
	}

	private void logError(String errorMessage) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.ERROR_LOG_FILE, true))) {
			writer.write(errorMessage + Constants.NEW_LINE);
		} catch (IOException e) {
			System.out.println(Constants.ERROR_WRITING_LOG + e.getMessage());
		}
	}

	public void updateEmployee(int empIdToUpdate, String fieldToUpdate, int newEmpId, String newFirstName,
			String newLastName, String newDepartment) {
		boolean employeeFound = false;
		for (Employee employee : employee) {
			if (employee.getEmpId() == empIdToUpdate) {
				employee.setEmpId(newEmpId);
				employee.setFirstName(newFirstName);
				employee.setLastName(newLastName);
				employee.setDepartment(newDepartment);
				System.out.println(Constants.UPDATE_SUCCESS_MESSAGE);
				employeeFound = true;
				break;
			}
		}
		if (!employeeFound) {
			System.out.println(Constants.EMPLOYEE_NOT_FOUND_ERROR);
		}
	}

	public void updateEmployee(int empIdToUpdate, String fieldToUpdate, String newValue) {
		boolean employeeFound = false;
		for (Employee employee : employee) {
			if (employee.getEmpId() == empIdToUpdate) {
				switch (fieldToUpdate.toLowerCase()) {
				case Constants.FIRST_NAME_FIELD:
					employee.setFirstName(newValue);
					break;
				case Constants.LAST_NAME_FIELD:
					employee.setLastName(newValue);
					break;
				case Constants.DEPARTMENT_FIELD:
					employee.setDepartment(newValue);
					break;
				default:
					throw new IllegalArgumentException(Constants.INVALID_FIELD_TO_UPDATE_ERROR + fieldToUpdate);
				}
				System.out.println(Constants.UPDATE_SUCCESS_MESSAGE);
				employeeFound = true;
				break;
			}
		}
		if (!employeeFound) {
			System.out.println(Constants.EMPLOYEE_NOT_FOUND_ERROR);
		}
	}

	public void deleteEmployee(EmployeeManager manager, Scanner scanner) {
		System.out.println(Constants.ENTER_EMPID_TO_DELETE);
		int empIdToDelete = scanner.nextInt();
		scanner.nextLine();

		if (!confirmAction(scanner, Constants.DELETE_EMP)) {
			System.out.println(Constants.DELETION_CANCELLED_MESSAGE);
			return;
		}

		if (manager.deleteEmployeeById(empIdToDelete)) {
			System.out.println(Constants.DELETE_SUCCESS_MESSAGE + empIdToDelete);
		} else {
			System.out.println(Constants.EMPLOYEE_NOT_FOUND_MESSAGE + empIdToDelete);
		}
	}

	public void deleteAllEmployees(EmployeeManager manager, Scanner scanner) {
		if (!confirmAction(scanner, Constants.DELETE_ALL)) {
			System.out.println(Constants.DELETION_CANCELLED_MESSAGE);
			return;
		}

		manager.deleteAllEmployees();
		System.out.println(Constants.DELETE_ALL_SUCCESS_MESSAGE);
	}

	private boolean confirmAction(Scanner scanner, String actionDescription) {
		System.out.println(Constants.CONFIRMATION_PROMPT + actionDescription + Constants.YES_NO_PROMPT);
		String confirmation = scanner.nextLine().trim().toLowerCase();
		return confirmation.equals("y");
	}

	public boolean deleteEmployeeById(int empIdToDelete) {
		Iterator<Employee> iterator = employee.iterator();
		while (iterator.hasNext()) {
			Employee emp = iterator.next();
			if (emp.getEmpId() == empIdToDelete) {
				deletedEmployees.add(emp);
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	public void commitChanges() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(Constants.FILE_NAME))) {
			writer.println(Constants.EMPLOYEE_HEADER);
			for (Employee emp : employee) {
				writer.println(emp.getEmpId() + Constants.CSV_DELIMITER + emp.getFirstName() + Constants.CSV_DELIMITER
						+ emp.getLastName() + Constants.CSV_DELIMITER + emp.getDepartment());
			}
			System.out.println(Constants.COMMIT_SUCCESS_MESSAGE);
		} catch (IOException e) {
			System.out.println(Constants.COMMIT_ERROR_MESSAGE + e.getMessage());
		}
	}

	public Vector<Employee> displayAllEmployees() {

		return new Vector<>(employee);
	}

	public void displaySortedRecordsOption(EmployeeManager manager, Scanner scanner) {
		System.out.println(Constants.SORT_BY_PROMPT);
		System.out.println(Constants.SORT_OPTION_EMPID);
		System.out.println(Constants.SORT_OPTION_FIRST_NAME);
		System.out.println(Constants.SORT_OPTION_LAST_NAME);
		System.out.print(Constants.ENTER_CHOICE_PROMPT);

		int sortOption = scanner.nextInt();
		scanner.nextLine();

		switch (sortOption) {
		case 1:
			manager.displaySortedEmployeeRecords(manager.displayAllEmployees(), Constants.SORT_OPTION_EMPID);
			break;
		case 2:
			manager.displaySortedEmployeeRecords(manager.displayAllEmployees(), Constants.SORT_OPTION_FIRST_NAME);
			break;
		case 3:
			manager.displaySortedEmployeeRecords(manager.displayAllEmployees(), Constants.SORT_OPTION_LAST_NAME);
			break;
		default:
			System.out.println(Constants.IN_VALID);
			break;
		}
	}

	public Employee getEmployeeByEmpId(int empId) {
		System.out.println(Constants.CSV_HEADER);
		for (Employee emp : employee) {
			if (emp.getEmpId() == empId) {
				return emp;
			}
		}
		return null;
	}

	public Vector<Employee> getEmployeesByEmpId(int empId) {
		Vector<Employee> employeesWithSameId = new Vector<>();
		for (Employee emp : displayedEmployees) {
			if (emp.getEmpId() == empId) {
				employeesWithSameId.add(emp);
			}
		}
		return employeesWithSameId;
	}

	public void deleteAllEmployees() {
		employee.clear();

	}

	public void displaySortedEmployeeRecords(Vector<Employee> employees, String sortOption) {
		Comparator<Employee> comparator = null;

		switch (sortOption) {
		case Constants.SORT_OPTION_EMPID:
			comparator = Comparator.comparingInt(Employee::getEmpId);
			break;
		case Constants.SORT_OPTION_FIRST_NAME:
			comparator = Comparator.comparing(Employee::getFirstName);
			break;
		case Constants.SORT_OPTION_LAST_NAME:
			comparator = Comparator.comparing(Employee::getLastName);
			break;
		default:
			System.out.println(Constants.INVALID_SORTING_OPTION);

			return;
		}

		Collections.sort(employees, comparator);

		System.out.println(Constants.EMPLOYEE_HEADER);
		for (Employee emp : employees) {
			System.out.println(emp);
		}

		storeSortedRecordsInCSV(employees);
	}

	public void displaySortedRecordsByOption(Comparator<Employee> comparator) {
		Vector<Employee> sortedEmployees = new Vector<>(displayedEmployees);
		Collections.sort(sortedEmployees, comparator);
		if (sortedEmployees.isEmpty()) {
			System.out.println(Constants.NO_EMPLOYEES_FOUND_MESSAGE);
		} else {
			System.out.println(Constants.SORTED_EMPLOYEES_MESSAGE);
			for (Employee emp : sortedEmployees) {
				System.out.println(emp);
			}

			storeSortedRecordsInCSV(sortedEmployees);
		}
	}

	private static void storeSortedRecordsInCSV(Vector<Employee> employees) {
		String sortedFileName = Constants.SORTED_FILE;
		try (PrintWriter writer = new PrintWriter(new FileWriter(sortedFileName))) {
			writer.println(Constants.EMPLOYEE_HEADER);
			for (Employee emp : employees) {
				writer.println(emp.getEmpId() + Constants.CSV_DELIMITER + emp.getFirstName() + Constants.CSV_DELIMITER
						+ emp.getLastName() + Constants.CSV_DELIMITER + emp.getDepartment());
			}
			System.out.println(Constants.STORE_SORTED_SUCCESS_MESSAGE + sortedFileName);
		} catch (IOException e) {
			System.out.println(Constants.STORE_SORTED_ERROR_MESSAGE + e.getMessage());
		}
	}

	static void Fileexists(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println(Constants.FILE + filename + Constants.CREATED);
			} catch (IOException e) {
				System.err.println(Constants.ERROR_FILE + e.getMessage());
			}
		}
	}

	public int getValidEmpId(Scanner scanner) {
		while (true) {
			try {
				int empId = Integer.parseInt(scanner.nextLine().trim());
				if (empId < 0) {
					System.out.println(Constants.ERROR_INVALID_EMPID);
					System.out.print(Constants.ENTER_EMPID + ": ");
					continue;
				}
				return empId;
			} catch (NumberFormatException e) {
				System.out.println(Constants.ERROR_INVALID_EMPID);
				System.out.print(Constants.ENTER_EMPID + ": ");
			}
		}
	}

	public String selectDepartment(Scanner scanner) {
		String department = Constants.UNKNOWN_DEPARTMENT;
		boolean validChoice = false;

		while (!validChoice) {
			try {
				System.out.println(Constants.ENTER_DEPARTMENT);
				System.out.println(Constants.DEPARTMENT_OPTIONS);
				System.out.print(Constants.SELECT_DEPARTMENT_PROMPT);

				int departmentChoice = scanner.nextInt();
				scanner.nextLine();

				switch (departmentChoice) {
				case 1:
					department = Constants.DEV_DEPARTMENT;
					validChoice = true;
					break;
				case 2:
					department = Constants.PS_DEPARTMENT;
					validChoice = true;
					break;
				case 3:
					department = Constants.QA_DEPARTMENT;
					validChoice = true;
					break;
				case 4:
					department = Constants.ADMIN_DEPARTMENT;
					validChoice = true;
					break;
				default:
					System.out.println(Constants.INVALID_DEPARTMENT_DEFAULTING);
				}
			} catch (InputMismatchException e) {
				scanner.nextLine();
				System.out.println(Constants.ERROR_INVALID_INPUT);
			}
		}

		return department;
	}

	public int getDeletionChoice(Scanner scanner) {
		int choice = 0;
		boolean validInput = false;
		while (!validInput) {
			try {
				System.out.println(Constants.DELETE_EMPLOYEE_OPTIONS);
				System.out.print(Constants.ENTER_CHOICE);
				choice = scanner.nextInt();
				scanner.nextLine();
				validInput = true;
			} catch (InputMismatchException e) {

				scanner.nextLine();
				System.out.println(Constants.INVALID_CHOICE);
			}
		}
		return choice;
	}

	public void displayAllEmployeesWithOption(Scanner scanner) {
		System.out.println(Constants.VECTOR_DATA);
		System.out.println(Constants.DIS_DATA);
		System.out.print(Constants.MENU_OPTION_ENTER_CHOICE);
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1:
			displayAllEmployeesInMemory();
			break;
		case 2:
			displayEmployeesFromFile();
			break;
		default:
			System.out.println(Constants.IC);
		}
	}

	private void displayEmployeesFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(Constants.FILE_NAME))) {

			String line;
			boolean dataFound = false;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				dataFound = true;
			}
			if (!dataFound) {
				System.out.println(Constants.NO_DATA_FOUND_MESSAGE);
			}
		} catch (IOException e) {
			System.out.println(Constants.READ_ERR + e.getMessage());
		}
	}
	 

//	public Vector<Employee> displayAllEmployeesInMemory() {
//		System.out.println(Constants.EMPLOYEE_HEADER);
//		if (employee.isEmpty()) {
//			System.out.println(Constants.NO_EMPLOYEES_FOUND_MESSAGE);
//		} else {
//			for (Employee emp : employee) {
//				System.out.println(emp);
//			}
//		}
//		return new Vector<>(employee);
//	}
	public Vector<Employee> displayAllEmployeesInMemory() {
	    // Load employees from the CSV file
	    System.out.println(Constants.EMPLOYEE_HEADER);
	    if (employee.isEmpty()) {
	        System.out.println(Constants.NO_EMPLOYEES_FOUND_MESSAGE);
	    } else {
	        for (Employee emp : employee) {
	            System.out.println(emp);
	        }
	    }
	    return new Vector<>(employee);
	}
	// Modify your displayEmployeesFromFile method to return the loaded data instead of printing it directly
	 

	// Modify your displayAllEmployeesInMemory method to take a Vector<Employee> parameter and print its contents
	public void displayAllEmployeesInMemory(Vector<Employee> employees) {
	    System.out.println(Constants.EMPLOYEE_HEADER);
	    if (employees.isEmpty()) {
	        System.out.println(Constants.NO_EMPLOYEES_FOUND_MESSAGE);
	    } else {
	        for (Employee emp : employees) {
	            System.out.println(emp);
	        }
	    }
	}

	public void commitAndExit() {
		saveEmployees();
		System.out.println(Constants.QUIT);
		System.exit(0);
	}

}
