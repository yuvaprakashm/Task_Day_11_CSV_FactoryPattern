package net.texala.employee.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import net.texala.employee.exceptions.EmployeeOperationException;
import net.texala.employee.factory.EmployeeFactory;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.service.EmployeeOperation;
import static net.texala.employee.constants.Constants.LINE;
import static net.texala.employee.constants.Constants.EMP_STM;
import static net.texala.employee.constants.Constants.SLINE;
import static net.texala.employee.constants.Constants.MENU_OPTION_ENTER_CHOICE;
import static net.texala.employee.constants.Constants.MENU_OPTION_ADD;
import static net.texala.employee.constants.Constants.MENU_OPTION_UPDATE;
import static net.texala.employee.constants.Constants.MENU_OPTION_DELETE;
import static net.texala.employee.constants.Constants.MENU_OPTION_DISPLAY;
import static net.texala.employee.constants.Constants.MENU_OPTION_COMMIT;
import static net.texala.employee.constants.Constants.MENU_OPTION_EXIT;
import static net.texala.employee.constants.Constants.EXIT;
import static net.texala.employee.constants.Constants.ERROR_INVALID_CHOICE;
import static net.texala.employee.constants.Constants.ERROR_INVALID_INPUT;

public class Main {
    public static void main(String[] args) {
        EmployeeFactory factory = new EmployeeFactory();
        Scanner scanner = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();
        
        boolean exit = false;
        while (!exit) {
            
            System.out.println(LINE);
            System.out.println(EMP_STM);
            System.out.println(LINE);
            System.out.println(SLINE + MENU_OPTION_ADD);
            System.out.println(SLINE + MENU_OPTION_UPDATE);
            System.out.println(SLINE + MENU_OPTION_DELETE);
            System.out.println(SLINE + MENU_OPTION_DISPLAY);
            System.out.println(SLINE + MENU_OPTION_COMMIT);
            System.out.println(SLINE + MENU_OPTION_EXIT);
            System.out.println(LINE);
            System.out.print(MENU_OPTION_ENTER_CHOICE);
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case EXIT:
                        manager.commitAndExit(); 
                        
                        exit = true;
                        break;
                    default:
                        EmployeeOperation operation = factory.createEmployeeOperation(choice);
                        if (operation != null) {
                            operation.execute(manager, scanner);
                        } else {
                            System.out.println(ERROR_INVALID_CHOICE);
                        }
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ERROR_INVALID_INPUT);
                scanner.nextLine();  
            } catch (EmployeeOperationException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
