package net.texala.employee.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import net.texala.employee.constants.Constants;
import net.texala.employee.model.Employee;

public class EmployeeManager {

	private Vector<Employee> employee;

	public EmployeeManager() {
		employee = new Vector<>();
		loadDataFromCSV();
	}

	private void loadDataFromCSV() {
		try (BufferedReader reader = new BufferedReader(new FileReader(Constants.FILE_NAME))) {
			String line;
			boolean headerSkipped = false;
			while ((line = reader.readLine()) != null) {
				if (!headerSkipped) {
					headerSkipped = true;
					continue;
				}
				String[] parts = line.split(Constants.CSV_DELIMITER);
				if (parts.length == Constants.NUMBER_OF_FIELDS) {
					int empId = Integer.parseInt(parts[Constants.EMPLOYEE_ID_INDEX]);
					String firstName = parts[Constants.FIRST_NAME_INDEX];
					String lastName = parts[Constants.LAST_NAME_INDEX];
					String department = parts[Constants.DEPARTMENT_INDEX];
					employee.add(new Employee(empId, firstName, lastName, department));
				}
			}
		} catch (IOException | NumberFormatException e) {
			System.out.println(Constants.LOAD_CSV_ERROR + e.getMessage());
		}
	}

	public void addEmployee(int empId, String firstName, String lastName, String department) {
		if (!isValidName(firstName) && !isValidName(lastName)) {
			System.out.println(Constants.INVALID_NAME_ERROR);
			logError(Constants.INVALID_NAME_LOG + firstName + lastName);
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

	public Vector<Employee> getAllEmployees() {
		return employee;
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
		Vector<Employee> sortedEmployees = new Vector<>(manager.getAllEmployees());
		Collections.sort(sortedEmployees, comparator);
		if (sortedEmployees.isEmpty()) {
			System.out.println(Constants.NO_EMPLOYEES_FOUND_MESSAGE);
		} else {
			System.out.println(Constants.SORTED_EMPLOYEE_RECORDS_HEADER);
			for (Employee emp : sortedEmployees) {
				System.out.println(emp);
			}
			storeSortedRecordsInCSV(sortedEmployees);
		}
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
}
