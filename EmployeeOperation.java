package net.texala.employee.service;

import java.util.Scanner;

import net.texala.employee.exceptions.EmployeeOperationException;
import net.texala.employee.manager.EmployeeManager;

public interface EmployeeOperation {
    void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException;
}
