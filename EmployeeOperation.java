package net.texala.employee.service;

import java.util.Scanner;

import net.texala.employee.exceptions.EmployeeOperationException;
import net.texala.employee.manager.EmployeeManager;
import net.texala.employee.model.Employee;

public interface EmployeeOperation {
    void execute(EmployeeManager manager, Scanner scanner) throws EmployeeOperationException;
}