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
import net.texala.employee.model.Employee;
import static net.texala.employee.constants.Constants.NEW_EMPLOYEES_FILE_CREATED;
import static net.texala.employee.constants.Constants.FILE_NAME;
import static net.texala.employee.constants.Constants.ERROR_CREATING_NEW_EMPLOYEES_FILE;
import static net.texala.employee.constants.Constants.CSV_DELIMITER;
import static net.texala.employee.constants.Constants.INVALID_EMPLOYEE_ID_IN_LINE;
import static net.texala.employee.constants.Constants.INVALID_DATA_FORMAT_IN_LINE;
import static net.texala.employee.constants.Constants.ERROR_LOADING_EMPLOYEES;
import static net.texala.employee.constants.Constants.ERROR_SAV_EMP;
import static net.texala.employee.constants.Constants.INVALID_NAME_ERROR;
import static net.texala.employee.constants.Constants.INVALID_NAME_LOG;
import static net.texala.employee.constants.Constants.INVALID_FIRST_NAME_ERROR;
import static net.texala.employee.constants.Constants.INVALID_FIRST_NAME_LOG;
import static net.texala.employee.constants.Constants.INVALID_LAST_NAME_ERROR;
import static net.texala.employee.constants.Constants.INVALID_LAST_NAME_LOG;
import static net.texala.employee.constants.Constants.DUPLICATE_EMPID_ERROR;
import static net.texala.employee.constants.Constants.ALREADY_EXISTS;

import static net.texala.employee.constants.Constants.DUPLICATE_EMPID_LOG;
import static net.texala.employee.constants.Constants.EMP_ADD;
import static net.texala.employee.constants.Constants.NAME_REGEX;
import static net.texala.employee.constants.Constants.ERROR_LOG_FILE;

import static net.texala.employee.constants.Constants.NEW_LINE;
import static net.texala.employee.constants.Constants.ERROR_WRITING_LOG;
import static net.texala.employee.constants.Constants.UPDATE_SUCCESS_MESSAGE;
import static net.texala.employee.constants.Constants.EMPLOYEE_NOT_FOUND_ERROR;
import static net.texala.employee.constants.Constants.FIRST_NAME_FIELD;
import static net.texala.employee.constants.Constants.LAST_NAME_FIELD;
import static net.texala.employee.constants.Constants.DEPARTMENT_FIELD;
import static net.texala.employee.constants.Constants.INVALID_FIELD_TO_UPDATE_ERROR;
import static net.texala.employee.constants.Constants.ENTER_EMPID_TO_DELETE;
import static net.texala.employee.constants.Constants.DELETION_CANCELLED_MESSAGE;

import static net.texala.employee.constants.Constants.DELETE_SUCCESS_MESSAGE;
import static net.texala.employee.constants.Constants.DELETE_ALL_SUCCESS_MESSAGE;
import static net.texala.employee.constants.Constants.EMPLOYEE_NOT_FOUND_MESSAGE;
import static net.texala.employee.constants.Constants.DELETE_EMP;
import static net.texala.employee.constants.Constants.DELETE_ALL;
import static net.texala.employee.constants.Constants.CONFIRMATION_PROMPT;
import static net.texala.employee.constants.Constants.YES_NO_PROMPT;
import static net.texala.employee.constants.Constants.EMPLOYEE_HEADER;
import static net.texala.employee.constants.Constants.COMMIT_ERROR_MESSAGE;
import static net.texala.employee.constants.Constants.COMMIT_SUCCESS_MESSAGE;
import static net.texala.employee.constants.Constants.SORT_BY_PROMPT;

import static net.texala.employee.constants.Constants.SORT_OPTION_EMPID;
import static net.texala.employee.constants.Constants.SORT_OPTION_FIRST_NAME;

import static net.texala.employee.constants.Constants.SORT_OPTION_LAST_NAME;
import static net.texala.employee.constants.Constants.ENTER_CHOICE_PROMPT;
import static net.texala.employee.constants.Constants.INVALID_SORTING_OPTION;
import static net.texala.employee.constants.Constants.CSV_HEADER;
import static net.texala.employee.constants.Constants.NO_EMPLOYEES_FOUND_MESSAGE;
import static net.texala.employee.constants.Constants.SORTED_EMPLOYEES_MESSAGE;
import static net.texala.employee.constants.Constants.SORTED_FILE;
import static net.texala.employee.constants.Constants.STORE_SORTED_SUCCESS_MESSAGE;
import static net.texala.employee.constants.Constants.STORE_SORTED_ERROR_MESSAGE;
import static net.texala.employee.constants.Constants.ERROR_FILE;
import static net.texala.employee.constants.Constants.FILE;
import static net.texala.employee.constants.Constants.CREATED;
import static net.texala.employee.constants.Constants.ENTER_EMPID;
import static net.texala.employee.constants.Constants.ERROR_INVALID_EMPID;
import static net.texala.employee.constants.Constants.UNKNOWN_DEPARTMENT;
import static net.texala.employee.constants.Constants.DEPARTMENT_OPTIONS;
import static net.texala.employee.constants.Constants.SELECT_DEPARTMENT_PROMPT;
import static net.texala.employee.constants.Constants.ENTER_DEPARTMENT;
import static net.texala.employee.constants.Constants.DEV_DEPARTMENT;
import static net.texala.employee.constants.Constants.QA_DEPARTMENT;
import static net.texala.employee.constants.Constants.PS_DEPARTMENT;
import static net.texala.employee.constants.Constants.ADMIN_DEPARTMENT;
import static net.texala.employee.constants.Constants.INVALID_DEPARTMENT_DEFAULTING;
import static net.texala.employee.constants.Constants.ERROR_INVALID_INPUT;
import static net.texala.employee.constants.Constants.ENTER_CHOICE;
import static net.texala.employee.constants.Constants.INVALID_CHOICE;
import static net.texala.employee.constants.Constants.DELETE_EMPLOYEE_OPTIONS;
import static net.texala.employee.constants.Constants.VECTOR_DATA;
import static net.texala.employee.constants.Constants.DIS_DATA;
import static net.texala.employee.constants.Constants.MENU_OPTION_ENTER_CHOICE;
import static net.texala.employee.constants.Constants.NO_DATA_FOUND_MESSAGE;
import static net.texala.employee.constants.Constants.READ_ERR;
import static net.texala.employee.constants.Constants.SEMI;
import static net.texala.employee.constants.Constants.QUIT;
import static net.texala.employee.constants.Constants.IC;
import static net.texala.employee.constants.Constants.IN_VALID;

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
		File file = new File(FILE_NAME);

		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println(NEW_EMPLOYEES_FILE_CREATED + FILE_NAME);

			} catch (IOException e) {
				System.out.println(ERROR_CREATING_NEW_EMPLOYEES_FILE + e.getMessage());

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
				String[] parts = line.split(CSV_DELIMITER);
				if (parts.length == 4) {
					try {
						int empId = Integer.parseInt(parts[0].trim());
						String firstName = parts[1].trim();
						String lastName = parts[2].trim();
						String department = parts[3].trim();
						employee.add(new Employee(empId, firstName, lastName, department));
					} catch (NumberFormatException e) {

						System.out.println(INVALID_EMPLOYEE_ID_IN_LINE + line);

					}
				} else {

					System.out.println(INVALID_DATA_FORMAT_IN_LINE + line);

				}
			}
		} catch (IOException e) {
			System.out.println(ERROR_LOADING_EMPLOYEES + e.getMessage());

		}
	}

	public void saveEmployees() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
			writer.println(EMPLOYEE_HEADER);
			for (Employee employee : employee) {
				writer.println(employee.getEmpId() + CSV_DELIMITER + employee.getFirstName() + CSV_DELIMITER
						+ employee.getLastName() + CSV_DELIMITER + employee.getDepartment());
			}
		} catch (IOException e) {
			System.out.println(ERROR_SAV_EMP + e.getMessage());
		}
	}

	public void addEmployee(int empId, String firstName, String lastName, String department) {
		if (!isValidName(firstName) && !isValidName(lastName)) {
			System.out.println(INVALID_NAME_ERROR);
			logError(INVALID_NAME_LOG + firstName + ',' + lastName);
			return;
		}
		if (!isValidName(firstName)) {
			System.out.println(INVALID_FIRST_NAME_ERROR);
			logError(INVALID_FIRST_NAME_LOG + firstName);
			return;
		}

		if (!isValidName(lastName)) {
			System.out.println(INVALID_LAST_NAME_ERROR);
			logError(INVALID_LAST_NAME_LOG + lastName);
			return;
		}

		if (isDuplicateEmpId(empId)) {
			System.out.println(DUPLICATE_EMPID_ERROR + empId + ALREADY_EXISTS);
			logError(DUPLICATE_EMPID_LOG + empId);
			return;
		}

		employee.add(new Employee(empId, firstName, lastName, department));
		System.out.println(EMP_ADD);
	}

	private boolean isValidName(String name) {
		return name.matches(NAME_REGEX);
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
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ERROR_LOG_FILE, true))) {
			writer.write(errorMessage + NEW_LINE);
		} catch (IOException e) {
			System.out.println(ERROR_WRITING_LOG + e.getMessage());
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
				System.out.println(UPDATE_SUCCESS_MESSAGE);
				employeeFound = true;
				break;
			}
		}
		if (!employeeFound) {
			System.out.println(EMPLOYEE_NOT_FOUND_ERROR);
		}
	}

	public void updateEmployee(int empIdToUpdate, String fieldToUpdate, String newValue) {
		boolean employeeFound = false;
		for (Employee employee : employee) {
			if (employee.getEmpId() == empIdToUpdate) {
				switch (fieldToUpdate.toLowerCase()) {
				case FIRST_NAME_FIELD:
					employee.setFirstName(newValue);
					break;
				case LAST_NAME_FIELD:
					employee.setLastName(newValue);
					break;
				case DEPARTMENT_FIELD:
					employee.setDepartment(newValue);
					break;
				default:
					throw new IllegalArgumentException(INVALID_FIELD_TO_UPDATE_ERROR + fieldToUpdate);
				}
				System.out.println(UPDATE_SUCCESS_MESSAGE);
				employeeFound = true;
				break;
			}
		}
		if (!employeeFound) {
			System.out.println(EMPLOYEE_NOT_FOUND_ERROR);
		}
	}

	public void deleteEmployee(EmployeeManager manager, Scanner scanner) {
		System.out.println(ENTER_EMPID_TO_DELETE);
		int empIdToDelete = scanner.nextInt();
		scanner.nextLine();

		if (!confirmAction(scanner, DELETE_EMP)) {
			System.out.println(DELETION_CANCELLED_MESSAGE);
			return;
		}

		if (manager.deleteEmployeeById(empIdToDelete)) {
			System.out.println(DELETE_SUCCESS_MESSAGE + empIdToDelete);
		} else {
			System.out.println(EMPLOYEE_NOT_FOUND_MESSAGE + empIdToDelete);
		}
	}

	public void deleteAllEmployees(EmployeeManager manager, Scanner scanner) {
		if (!confirmAction(scanner, DELETE_ALL)) {
			System.out.println(DELETION_CANCELLED_MESSAGE);
			return;
		}

		manager.deleteAllEmployees();
		System.out.println(DELETE_ALL_SUCCESS_MESSAGE);
	}

	private boolean confirmAction(Scanner scanner, String actionDescription) {
		System.out.println(CONFIRMATION_PROMPT + actionDescription + YES_NO_PROMPT);
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
		try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
			writer.println(EMPLOYEE_HEADER);
			for (Employee emp : employee) {
				writer.println(emp.getEmpId() + CSV_DELIMITER + emp.getFirstName() + CSV_DELIMITER + emp.getLastName()
						+ CSV_DELIMITER + emp.getDepartment());
			}
			System.out.println(COMMIT_SUCCESS_MESSAGE);
		} catch (IOException e) {
			System.out.println(COMMIT_ERROR_MESSAGE + e.getMessage());
		}
	}

	public Vector<Employee> displayAllEmployees() {

		return new Vector<>(employee);
	}

	public void displaySortedRecordsOption(EmployeeManager manager, Scanner scanner) {
		System.out.println(SORT_BY_PROMPT);
		System.out.println(SORT_OPTION_EMPID);
		System.out.println(SORT_OPTION_FIRST_NAME);
		System.out.println(SORT_OPTION_LAST_NAME);
		System.out.print(ENTER_CHOICE_PROMPT);

		int sortOption = scanner.nextInt();
		scanner.nextLine();

		switch (sortOption) {
		case 1:
			manager.displaySortedEmployeeRecords(manager.displayAllEmployees(), SORT_OPTION_EMPID);
			break;
		case 2:
			manager.displaySortedEmployeeRecords(manager.displayAllEmployees(), SORT_OPTION_FIRST_NAME);
			break;
		case 3:
			manager.displaySortedEmployeeRecords(manager.displayAllEmployees(), SORT_OPTION_LAST_NAME);
			break;
		default:
			System.out.println(IN_VALID);
			break;
		}
	}

	public Employee getEmployeeByEmpId(int empId) {

		for (Employee emp : employee) {

			if (emp.getEmpId() == empId) {
				System.out.println(CSV_HEADER);
				return emp;
			}
		}
		return null;
	}

	public Employee getEmpByEmpId(int empId) {

		for (Employee emp : employee) {

			if (emp.getEmpId() == empId) {

				return emp;
			}
		}
		return null;
	}

	public Vector<Employee> getEmployeeByEmpoyeeId(int empId) {
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
		case SORT_OPTION_EMPID:
			comparator = Comparator.comparingInt(Employee::getEmpId);
			break;
		case SORT_OPTION_FIRST_NAME:
			comparator = Comparator.comparing(Employee::getFirstName);
			break;
		case SORT_OPTION_LAST_NAME:
			comparator = Comparator.comparing(Employee::getLastName);
			break;
		default:
			System.out.println(INVALID_SORTING_OPTION);

			return;
		}

		Collections.sort(employees, comparator);

		System.out.println(EMPLOYEE_HEADER);
		for (Employee emp : employees) {
			System.out.println(emp);
		}

		storeSortedRecordsInCSV(employees);
	}

	public void displaySortedRecordsByOption(Comparator<Employee> comparator) {
		Vector<Employee> sortedEmployees = new Vector<>(displayedEmployees);
		Collections.sort(sortedEmployees, comparator);
		if (sortedEmployees.isEmpty()) {
			System.out.println(NO_EMPLOYEES_FOUND_MESSAGE);
		} else {
			System.out.println(SORTED_EMPLOYEES_MESSAGE);
			for (Employee emp : sortedEmployees) {
				System.out.println(emp);
			}

			storeSortedRecordsInCSV(sortedEmployees);
		}
	}

	private static void storeSortedRecordsInCSV(Vector<Employee> employees) {
		String sortedFileName = SORTED_FILE;
		try (PrintWriter writer = new PrintWriter(new FileWriter(sortedFileName))) {
			writer.println(EMPLOYEE_HEADER);
			for (Employee emp : employees) {
				writer.println(emp.getEmpId() + CSV_DELIMITER + emp.getFirstName() + CSV_DELIMITER + emp.getLastName()
						+ CSV_DELIMITER + emp.getDepartment());
			}
			System.out.println(STORE_SORTED_SUCCESS_MESSAGE + sortedFileName);
		} catch (IOException e) {
			System.out.println(STORE_SORTED_ERROR_MESSAGE + e.getMessage());
		}
	}

	static void Fileexists(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println(FILE + filename + CREATED);
			} catch (IOException e) {
				System.err.println(ERROR_FILE + e.getMessage());
			}
		}
	}

	public int getValidEmpId(Scanner scanner) {
		while (true) {
			try {
				int empId = Integer.parseInt(scanner.nextLine().trim());
				if (empId < 0) {
					System.out.println(ERROR_INVALID_EMPID);
					System.out.print(ENTER_EMPID + SEMI);
					continue;
				}
				return empId;
			} catch (NumberFormatException e) {
				System.out.println(ERROR_INVALID_EMPID);
				System.out.print(ENTER_EMPID + SEMI);
			}
		}
	}

	public String selectDepartment(Scanner scanner) {
		String department = UNKNOWN_DEPARTMENT;
		boolean validChoice = false;

		while (!validChoice) {
			try {
				System.out.println(ENTER_DEPARTMENT);
				System.out.println(DEPARTMENT_OPTIONS);
				System.out.print(SELECT_DEPARTMENT_PROMPT);

				int departmentChoice = scanner.nextInt();
				scanner.nextLine();

				switch (departmentChoice) {
				case 1:
					department = DEV_DEPARTMENT;
					validChoice = true;
					break;
				case 2:
					department = PS_DEPARTMENT;
					validChoice = true;
					break;
				case 3:
					department = QA_DEPARTMENT;
					validChoice = true;
					break;
				case 4:
					department = ADMIN_DEPARTMENT;
					validChoice = true;
					break;
				default:
					System.out.println(INVALID_DEPARTMENT_DEFAULTING);
				}
			} catch (InputMismatchException e) {
				scanner.nextLine();
				System.out.println(ERROR_INVALID_INPUT);
			}
		}

		return department;
	}

	public int getDeletionChoice(Scanner scanner) {
		int choice = 0;
		boolean validInput = false;
		while (!validInput) {
			try {
				System.out.println(DELETE_EMPLOYEE_OPTIONS);
				System.out.print(ENTER_CHOICE);
				choice = scanner.nextInt();
				scanner.nextLine();
				validInput = true;
			} catch (InputMismatchException e) {

				scanner.nextLine();
				System.out.println(INVALID_CHOICE);
			}
		}
		return choice;
	}

	public void displayAllEmployeesWithOption(Scanner scanner) {
		System.out.println(VECTOR_DATA);
		System.out.println(DIS_DATA);
		System.out.print(MENU_OPTION_ENTER_CHOICE);
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
			System.out.println(IC);
		}
	}

	private void displayEmployeesFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

			String line;
			boolean dataFound = false;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				dataFound = true;
			}

			if (!dataFound) {
				System.out.println(NO_DATA_FOUND_MESSAGE);
			}
		} catch (IOException e) {
			System.out.println(READ_ERR + e.getMessage());
		}
	}

	public Vector<Employee> displayAllEmployeesInMemory() {
		if (employee.isEmpty()) {
			System.out.println(NO_EMPLOYEES_FOUND_MESSAGE);
		} else {
			System.out.println(EMPLOYEE_HEADER);
			for (Employee emp : employee) {
				System.out.println(emp);
			}
		}
		return new Vector<>(employee);
	}

	public void commitAndExit() {
		saveEmployees();
		System.out.println(QUIT);

		System.exit(0);
	}

}
