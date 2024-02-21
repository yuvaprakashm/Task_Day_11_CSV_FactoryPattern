package net.texala.employee.impl;

import java.util.Scanner;
import net.texala.employee.exceptions.EmployeeOperationException;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeOperation;
import static net.texala.employee.constants.Constants.EMPLOYEE_NOT_FOUND;
import static net.texala.employee.constants.Constants.UPDATE_OPTION_FIRST_NAME;
import static net.texala.employee.constants.Constants.UPDATE_OPTION_LAST_NAME;
import static net.texala.employee.constants.Constants.UPDATE_ALL;
import static net.texala.employee.constants.Constants.UPDATE_OPTION_DEPARTMENT;
import static net.texala.employee.constants.Constants.UPDATE_OPTION_ALL;
import static net.texala.employee.constants.Constants.SELECT_FIELD;
import static net.texala.employee.constants.Constants.FIELD_FIRST_NAME;
import static net.texala.employee.constants.Constants.FIELD_LAST_NAME;
import static net.texala.employee.constants.Constants.FIELD_DEPARTMENT;
import static net.texala.employee.constants.Constants.UPDATE_ALL_STRING;
import static net.texala.employee.constants.Constants.ENTER_NEW_VALUE;
import static net.texala.employee.constants.Constants.FIELD_DEPARTMENT_STRING;
import static net.texala.employee.constants.Constants.ENTER_NEW_DEPARTMENT;
import static net.texala.employee.constants.Constants.PS_OPTION;
import static net.texala.employee.constants.Constants.DEV_OPTION;
import static net.texala.employee.constants.Constants.QA_OPTION;
import static net.texala.employee.constants.Constants.ADMIN_OPTION;
import static net.texala.employee.constants.Constants.SELECT_DEPARTMENT;
import static net.texala.employee.constants.Constants.DEV_OPTION_CHOICE;
import static net.texala.employee.constants.Constants.DEV_STRING;
import static net.texala.employee.constants.Constants.QA_STRING;
import static net.texala.employee.constants.Constants.PS_STRING;
import static net.texala.employee.constants.Constants.ADMIN_STRING;
import static net.texala.employee.constants.Constants.QA_OPTION_CHOICE;
import static net.texala.employee.constants.Constants.ADMIN_OPTION_CHOICE;
import static net.texala.employee.constants.Constants.INVALID_DEPARTMENT_CHOICE;
import static net.texala.employee.constants.Constants.UNKNOWN_STRING;
import static net.texala.employee.constants.Constants.UPDATE_EMPLOYEE_ERROR_MESSAGE;
import static net.texala.employee.constants.Constants.PS_OPTION_CHOICE;
import static net.texala.employee.constants.Constants.ENTER_EMPID_TO_UPDATE;
import static net.texala.employee.constants.Constants.INVALID_EMPLOYEE_ID;
import static net.texala.employee.constants.Constants.INVALID_FIELD_CHOICE;
import static net.texala.employee.constants.Constants.FIELD_FIRST_NAME_STRING;
import static net.texala.employee.constants.Constants.FIELD_LAST_NAME_STRING;
import static net.texala.employee.constants.Constants.ENTER_NEW_LAST_NAME;
import static net.texala.employee.constants.Constants.ENTER_NEW_FIRST_NAME;
import static net.texala.employee.constants.Constants.ENTER_NEW_VALUES;
import static net.texala.employee.constants.Constants.ENTER_NEW_EMPID;

public class UpdateEmployee implements EmployeeOperation {

    @Override
    public void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException {
        try {
            boolean validEmployeeId = false;
            int empIdToUpdate = 0;

            while (!validEmployeeId) {
                System.out.println(ENTER_EMPID_TO_UPDATE);
                try {
                    empIdToUpdate = Integer.parseInt(scanner.nextLine());
                    validEmployeeId = true;
                } catch (NumberFormatException e) {
                    System.out.println(INVALID_EMPLOYEE_ID);
                }
            }

            Employee employeeToUpdate = manager.getEmpByEmpId(empIdToUpdate);
            if (employeeToUpdate == null) {
                System.out.println(String.format(EMPLOYEE_NOT_FOUND, empIdToUpdate));
                return;
            }

            
            System.out.println(UPDATE_OPTION_FIRST_NAME);
            System.out.println(UPDATE_OPTION_LAST_NAME);
            System.out.println(UPDATE_OPTION_DEPARTMENT);
            System.out.println(UPDATE_OPTION_ALL);
            System.out.print(SELECT_FIELD + " ");

            int fieldChoice = scanner.nextInt();
            scanner.nextLine();

            String fieldToUpdate;
            switch (fieldChoice) {
                case FIELD_FIRST_NAME:
                    fieldToUpdate =  FIELD_FIRST_NAME_STRING;
                    break;
                case FIELD_LAST_NAME:
                    fieldToUpdate =  FIELD_LAST_NAME_STRING;
                    break;
                case FIELD_DEPARTMENT:
                    fieldToUpdate =  FIELD_DEPARTMENT_STRING;
                    break;
                case UPDATE_ALL:
                    fieldToUpdate =  UPDATE_ALL_STRING;
                    break;
                default:
                    System.out.println(INVALID_FIELD_CHOICE);
                    return;
            }

            if (!fieldToUpdate.equals(UPDATE_ALL_STRING)) {
                if (!fieldToUpdate.equals(FIELD_DEPARTMENT_STRING)) {
                    System.out.println(ENTER_NEW_VALUE);
                    String newValue = scanner.nextLine();
                    manager.updateEmployee(empIdToUpdate, fieldToUpdate, newValue);
                } else {
                    System.out.println(ENTER_NEW_DEPARTMENT);
                    System.out.println(DEV_OPTION);
                    System.out.println(PS_OPTION);
                    System.out.println(QA_OPTION);
                    System.out.println(ADMIN_OPTION);
                    System.out.print(SELECT_DEPARTMENT + " ");

                    int departmentChoice = scanner.nextInt();
                    scanner.nextLine();

                    String newDepartment;
                    switch (departmentChoice) {
                        case  DEV_OPTION_CHOICE:
                            newDepartment = DEV_STRING;
                            break;
                        case PS_OPTION_CHOICE:
                            newDepartment = PS_STRING;
                            break;
                        case QA_OPTION_CHOICE:
                            newDepartment = QA_STRING;
                            break;
                        case ADMIN_OPTION_CHOICE:
                            newDepartment = ADMIN_STRING;
                            break;
                        default:
                            System.out.println(INVALID_DEPARTMENT_CHOICE);
                            newDepartment = UNKNOWN_STRING;
                            break;
                    }

                    manager.updateEmployee(empIdToUpdate, fieldToUpdate, newDepartment);
                }
            } else {
                System.out.println(ENTER_NEW_VALUES);
                System.out.println(ENTER_NEW_EMPID + " ");
                int newEmpId = scanner.nextInt();
                scanner.nextLine();
                
                System.out.println(ENTER_NEW_FIRST_NAME + " ");
                String newFirstName = scanner.nextLine();
                
                System.out.println(ENTER_NEW_LAST_NAME + " ");
                String newLastName = scanner.nextLine();
                
                System.out.println(ENTER_NEW_DEPARTMENT);
                System.out.println(DEV_OPTION);
                System.out.println(PS_OPTION);
                System.out.println(QA_OPTION);
                System.out.println(ADMIN_OPTION);
                System.out.print(SELECT_DEPARTMENT + " ");
                
                int departmentChoice = scanner.nextInt();
                scanner.nextLine();
                
                String newDepartment;
                switch (departmentChoice) {
                    case DEV_OPTION_CHOICE:
                        newDepartment = DEV_STRING;
                        break;
                    case PS_OPTION_CHOICE:
                        newDepartment = PS_STRING;
                        break;
                    case QA_OPTION_CHOICE:
                        newDepartment = QA_STRING;
                        break;
                    case ADMIN_OPTION_CHOICE:
                        newDepartment = ADMIN_STRING;
                        break;
                    default:
                        System.out.println(INVALID_DEPARTMENT_CHOICE);
                        newDepartment = UNKNOWN_STRING;
                        break;
                }
                manager.updateEmployee(empIdToUpdate, fieldToUpdate, newEmpId, newFirstName, newLastName, newDepartment);
            }
        
        } catch (Exception e) {
            throw new EmployeeOperationException(UPDATE_EMPLOYEE_ERROR_MESSAGE + e.getMessage());
        }
    }
}
