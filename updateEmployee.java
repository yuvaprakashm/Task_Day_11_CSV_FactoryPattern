package net.texala.employee.impl;

import java.util.Scanner;
import net.texala.employee.constants.Constants;
import net.texala.employee.exceptions.EmployeeOperationException;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeOperation;

public class UpdateEmployee implements EmployeeOperation {

    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
            boolean validEmployeeId = false;
            int empIdToUpdate = 0;

            while (!validEmployeeId) {
                System.out.println(Constants.ENTER_EMPID_TO_UPDATE);
                try {
                    empIdToUpdate = Integer.parseInt(scanner.nextLine());
                    validEmployeeId = true;
                } catch (NumberFormatException e) {
                    System.out.println(Constants.INVALID_EMPLOYEE_ID);
                }
            }

            Employee employeeToUpdate = manager.getEmployeeByEmpId(empIdToUpdate);
            if (employeeToUpdate == null) {
                System.out.println(String.format(Constants.EMPLOYEE_NOT_FOUND, empIdToUpdate));
                return;
            }

            
            System.out.println(Constants.UPDATE_OPTION_FIRST_NAME);
            System.out.println(Constants.UPDATE_OPTION_LAST_NAME);
            System.out.println(Constants.UPDATE_OPTION_DEPARTMENT);
            System.out.println(Constants.UPDATE_OPTION_ALL);
            System.out.print(Constants.SELECT_FIELD + " ");

            int fieldChoice = scanner.nextInt();
            scanner.nextLine();

            String fieldToUpdate;
            switch (fieldChoice) {
                case Constants.FIELD_FIRST_NAME:
                    fieldToUpdate = Constants.FIELD_FIRST_NAME_STRING;
                    break;
                case Constants.FIELD_LAST_NAME:
                    fieldToUpdate = Constants.FIELD_LAST_NAME_STRING;
                    break;
                case Constants.FIELD_DEPARTMENT:
                    fieldToUpdate = Constants.FIELD_DEPARTMENT_STRING;
                    break;
                case Constants.UPDATE__ALL:
                    fieldToUpdate = Constants.UPDATE_ALL_STRING;
                    break;
                default:
                    System.out.println(Constants.INVALID_FIELD_CHOICE);
                    return;
            }

            if (!fieldToUpdate.equals(Constants.UPDATE_ALL_STRING)) {
                if (!fieldToUpdate.equals(Constants.FIELD_DEPARTMENT_STRING)) {
                    System.out.println(Constants.ENTER_NEW_VALUE);
                    String newValue = scanner.nextLine();
                    manager.updateEmployee(empIdToUpdate, fieldToUpdate, newValue);
                } else {
                    System.out.println(Constants.ENTER_NEW_DEPARTMENT);
                    System.out.println(Constants.DEV_OPTION);
                    System.out.println(Constants.PS_OPTION);
                    System.out.println(Constants.QA_OPTION);
                    System.out.println(Constants.ADMIN_OPTION);
                    System.out.print(Constants.SELECT_DEPARTMENT + " ");

                    int departmentChoice = scanner.nextInt();
                    scanner.nextLine();

                    String newDepartment;
                    switch (departmentChoice) {
                        case Constants.DEV_OPTION_CHOICE:
                            newDepartment = Constants.DEV_STRING;
                            break;
                        case Constants.PS_OPTION_CHOICE:
                            newDepartment = Constants.PS_STRING;
                            break;
                        case Constants.QA_OPTION_CHOICE:
                            newDepartment = Constants.QA_STRING;
                            break;
                        case Constants.ADMIN_OPTION_CHOICE:
                            newDepartment = Constants.ADMIN_STRING;
                            break;
                        default:
                            System.out.println(Constants.INVALID_DEPARTMENT_CHOICE);
                            newDepartment = Constants.UNKNOWN_STRING;
                            break;
                    }

                    manager.updateEmployee(empIdToUpdate, fieldToUpdate, newDepartment);
                }
            } else {
                System.out.println(Constants.ENTER_NEW_VALUES);
                System.out.println(Constants.ENTER_NEW_EMPID + " ");
                int newEmpId = scanner.nextInt();
                scanner.nextLine();
                
                System.out.println(Constants.ENTER_NEW_FIRST_NAME + " ");
                String newFirstName = scanner.nextLine();
                
                System.out.println(Constants.ENTER_NEW_LAST_NAME + " ");
                String newLastName = scanner.nextLine();
                
                System.out.println(Constants.ENTER_NEW_DEPARTMENT);
                System.out.println(Constants.DEV_OPTION);
                System.out.println(Constants.PS_OPTION);
                System.out.println(Constants.QA_OPTION);
                System.out.println(Constants.ADMIN_OPTION);
                System.out.print(Constants.SELECT_DEPARTMENT + " ");
                
                int departmentChoice = scanner.nextInt();
                scanner.nextLine();
                
                String newDepartment;
                switch (departmentChoice) {
                    case Constants.DEV_OPTION_CHOICE:
                        newDepartment = Constants.DEV_STRING;
                        break;
                    case Constants.PS_OPTION_CHOICE:
                        newDepartment = Constants.PS_STRING;
                        break;
                    case Constants.QA_OPTION_CHOICE:
                        newDepartment = Constants.QA_STRING;
                        break;
                    case Constants.ADMIN_OPTION_CHOICE:
                        newDepartment = Constants.ADMIN_STRING;
                        break;
                    default:
                        System.out.println(Constants.INVALID_DEPARTMENT_CHOICE);
                        newDepartment = Constants.UNKNOWN_STRING;
                        break;
                }
                manager.updateEmployee(empIdToUpdate, fieldToUpdate, newEmpId, newFirstName, newLastName, newDepartment);
            }
        
        } catch (Exception e) {
            throw new EmployeeOperationException(Constants.UPDATE_EMPLOYEE_ERROR_MESSAGE + e.getMessage());
        }
    }
}
