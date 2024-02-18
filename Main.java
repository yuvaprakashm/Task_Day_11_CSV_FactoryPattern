package net.texala.employee.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import net.texala.employee.constants.Constants;
import net.texala.employee.exceptions.EmployeeOperationException;
import net.texala.employee.factory.EmployeeFactory;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;

public class Main {
    public static void main(String[] args) throws EmployeeOperationException {
        EmployeeFactory factory = new EmployeeFactory();
        Scanner scanner = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();

        boolean exit = false;
        while (!exit) {
            System.out.println("1. " + Constants.MENU_OPTION_ADD);
            System.out.println("2. " + Constants.MENU_OPTION_UPDATE);
            System.out.println("3. " + Constants.MENU_OPTION_DELETE);
            System.out.println("4. " + Constants.MENU_OPTION_DISPLAY);
            System.out.println("5. " + Constants.MENU_OPTION_COMMIT);
            System.out.println("6. " + Constants.MENU_OPTION_EXIT);
            System.out.print(Constants.MENU_OPTION_ENTER_CHOICE);

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == Constants.EXIT) {
                    exit = true;
                    continue;
                }

                EmployeeOperation operation = factory.createEmployeeOperation(choice);
                if (operation != null) {
                    operation.execute(manager, scanner);
                } else {
                	System.out.println(Constants.ERROR_INVALID_CHOICE);
                }
            } catch (InputMismatchException e) {
            	 System.out.println(Constants.ERROR_INVALID_INPUT);
                scanner.nextLine();
            }  
        }
        scanner.close();
    }
}
