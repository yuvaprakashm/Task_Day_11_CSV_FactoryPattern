package net.texala.employee.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

	public EmployeeManager() {
		employee = new Vector<>();
		loadDataFromCSV();
	}

	public void loadDataFromCSV() {
		try {
			File file = new File(Constants.FILE_NAME);
			if (!file.exists()) {

				file.createNewFile();
				System.out.println(Constants.NEW_CSV);
				return;
			}

			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				System.out.println(line);
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println(Constants.LOAD_CSV_ERROR + e.getMessage());
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
		for (Employee employee : employee) {
			if (employee.getEmpId() == empIdToUpdate) {
				employee.setEmpId(newEmpId);
				employee.setFirstName(newFirstName);
				employee.setLastName(newLastName);
				employee.setDepartment(newDepartment);
				System.out.println(Constants.UPDATE_SUCCESS_MESSAGE);
				return;
			}
		}
		System.out.println(Constants.EMPLOYEE_NOT_FOUND_ERROR);
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
			throw new IllegalArgumentException(Constants.EMPLOYEE_NOT_FOUND_ERROR + empIdToUpdate);
		}
	}

	public void deleteEmployees(int empIdToDelete) {
		if (empIdToDelete != Constants.EMPTY_EMP_ID) {
			boolean deleted = deleteEmployeeById(empIdToDelete);
			if (deleted) {
				System.out.println(Constants.DELETE_SUCCESS_MESSAGE + empIdToDelete);
			} else {
				System.out.println(Constants.EMPLOYEE_NOT_FOUND_MESSAGE + empIdToDelete);
			}
		} else {
			employee.clear();
		}
	}

	public boolean deleteEmployeeById(int empIdToDelete) {
		Iterator<Employee> iterator = employee.iterator();
		while (iterator.hasNext()) {
			Employee emp = iterator.next();
			if (emp.getEmpId() == empIdToDelete) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	public void commitChanges() {
		try (FileWriter fileWriter = new FileWriter(Constants.FILE_NAME)) {
			fileWriter.write(Constants.CSV_HEADER);
			for (Employee employee : employee) {
				fileWriter.write(employee.getEmpId() + Constants.CSV_DELIMITER + employee.getFirstName()
						+ Constants.CSV_DELIMITER + employee.getLastName() + Constants.CSV_DELIMITER
						+ employee.getDepartment() + Constants.NEW_LINE);
			}
			System.out.println(Constants.COMMIT_SUCCESS_MESSAGE);
		} catch (IOException e) {
			System.out.println(Constants.COMMIT_ERROR_MESSAGE + e.getMessage());
		}
	}

	public Vector<Employee> displayAllEmployees() {
		System.out.println(Constants.EMPLOYEE_HEADER);
		return employee;
	}

	public Employee getEmployeeByEmpId(int empId) {

		for (Employee emp : employee) {
			if (emp.getEmpId() == empId) {
				return emp;
			}
		}
		return null;
	}

	public Vector<Employee> getEmployeesByEmpId(int empId) {
		 
		Vector<Employee> employeesWithSameId = new Vector<>();
		for (Employee emp : employee) {
			if (emp.getEmpId() == empId) {
				employeesWithSameId.add(emp);
			}
		}
		return employeesWithSameId;
	}

	public void deleteAllEmployees() {
		employee.clear();

	}

	public static void displaySortedEmployeeRecords(EmployeeManager manager, Comparator<Employee> comparator) {
		Vector<Employee> sortedEmployees = new Vector<>(manager.displayAllEmployees());
		Collections.sort(sortedEmployees, comparator);
		if (sortedEmployees.isEmpty()) {
			System.out.println(Constants.NO_EMPLOYEES_FOUND_MESSAGE);
		} else {

			 
			for (Employee emp : sortedEmployees) {
				System.out.println(emp);
			}

			storeSortedRecordsInCSV(sortedEmployees);
		}
	}

	public void displaySortedRecordsByOption(EmployeeManager manager, Scanner scanner) {
		System.out.println(Constants.SORT_BY);
		System.out.println(Constants.SORT_OPTION_EMPID);
		System.out.println(Constants.SORT_OPTION_FIRST_NAME);
		System.out.println(Constants.SORT_OPTION_LAST_NAME);
		System.out.print(Constants.ENTER_CHOICE);
		int sortOption = scanner.nextInt();
		scanner.nextLine();

		Comparator<Employee> comparator;

		switch (sortOption) {
		case 1:
			comparator = Comparator.comparingInt(Employee::getEmpId);
			break;
		case 2:
			comparator = Comparator.comparing(Employee::getFirstName);
			break;
		case 3:
			comparator = Comparator.comparing(Employee::getLastName);
			break;
		default:
			System.out.println(Constants.INVALID_OPTION_SELECTED);
			return;
		}

		EmployeeManager.displaySortedEmployeeRecords(manager, comparator);
	}

	private static void storeSortedRecordsInCSV(Vector<Employee> employees) {
		String sortedFileName = Constants.SORTED_FILE;
		try (FileWriter fileWriter = new FileWriter(sortedFileName)) {
		 fileWriter.write(Constants.CSV_HEADER);
			for (Employee emp : employees) {
				fileWriter.write(emp.getEmpId() + Constants.CSV_DELIMITER + emp.getFirstName() + Constants.CSV_DELIMITER
						+ emp.getLastName() + Constants.CSV_DELIMITER + emp.getDepartment() + Constants.NEW_LINE);
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

	 
}
