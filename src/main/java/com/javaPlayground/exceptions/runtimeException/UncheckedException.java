package com.javaPlayground.exceptions.runtimeException;

import com.javaPlayground.exceptions.exception.checked.ApplicationException;
import com.javaPlayground.exceptions.exception.checked.BusinessException;
import com.javaPlayground.exceptions.exception.checked.DatabaseTimeoutException;
import com.javaPlayground.exceptions.runtimeException.unchecked.ApplicationRuntimeException;
import com.javaPlayground.exceptions.runtimeException.unchecked.EmailValidationException;
import com.javaPlayground.exceptions.runtimeException.unchecked.ValidationException;
import org.springframework.dao.DataAccessException;

public class UncheckedException {

    public static void main(String[] args) {
        try {
            simulateCheckedException();
        } catch (DatabaseTimeoutException e) {
            System.out.println("Caught checked (deepest): " + e.getClass().getSimpleName());
        } catch (DataAccessException e) {
            System.out.println("Caught checked (data): " + e.getClass().getSimpleName());
        } catch (BusinessException e) {
            System.out.println("Caught checked (business): " + e.getClass().getSimpleName());
        } catch (ApplicationException e) {
            System.out.println("Caught checked (application): " + e.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("Caught generic Exception: " + e.getClass().getSimpleName());
        }

        System.out.println("------------------------------------------------");

        try {
            simulateUncheckedException();
        } catch (EmailValidationException e) {
            System.out.println("Caught unchecked (deepest): " + e.getClass().getSimpleName());
        } catch (ValidationException e) {
            System.out.println("Caught unchecked (validation): " + e.getClass().getSimpleName());
        } catch (SecurityException e) {
            System.out.println("Caught unchecked (security): " + e.getClass().getSimpleName());
        } catch (ApplicationRuntimeException e) {
            System.out.println("Caught unchecked (application runtime): " + e.getClass().getSimpleName());
        } catch (Throwable e) {
            System.out.println("Caught generic Throwable: " + e.getClass().getSimpleName());
        }

    }

    // ---------------- Helper Methods ----------------

    // Checked branch
    static void simulateCheckedException() throws ApplicationException {
        throw new DatabaseTimeoutException("Database connection timed out after 5 seconds");
    }

    // Unchecked branch
    static void simulateUncheckedException() throws Throwable {
        throw new Throwable("Email address format invalid: missing '@' symbol");
    }
}
