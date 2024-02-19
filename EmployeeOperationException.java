package net.texala.employee.exceptions;

public class EmployeeOperationException extends Exception {
    public EmployeeOperationException(String message) {
        super(message);
    }

    public static void throwAddEmployeeException(String message) throws EmployeeOperationException {
        throw new EmployeeOperationException(message);
    }

    public static void throwCommitEmployeeException(String message) throws EmployeeOperationException {
        throw new EmployeeOperationException(message);
    }

    public static void throwDeleteEmployeeException(String message) throws EmployeeOperationException {
        throw new EmployeeOperationException(message);
    }

    public static void throwDisplayEmployeeException(String message) throws EmployeeOperationException {
        throw new EmployeeOperationException(message);
    }

    public static void throwUpdateEmployeeException(String message) throws EmployeeOperationException {
        throw new EmployeeOperationException(message);
    }
}
