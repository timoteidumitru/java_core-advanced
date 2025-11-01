package com.javaPlayground.exceptions.exception;

import com.javaPlayground.exceptions.exception.checked.ApplicationException;
import com.javaPlayground.exceptions.exception.checked.BusinessException;
import com.javaPlayground.exceptions.exception.checked.DatabaseTimeoutException;
import org.springframework.dao.DataAccessException;

public class CheckedException {
    public static void main(String[] args) {
        try {
            simulateDatabaseOperation();
        } catch (DatabaseTimeoutException e) {
            System.out.println("Caught most specific: " + e.getClass().getSimpleName());
        } catch (DataAccessException e) {
            System.out.println("Caught data layer: " + e.getClass().getSimpleName());
        } catch (BusinessException e) {
            System.out.println("Caught business layer: " + e.getClass().getSimpleName());
        } catch (ApplicationException e) {
            System.out.println("Caught top-level application exception: " + e.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("Caught general exception: " + e.getClass().getSimpleName());
        }
    }

    // Simulates a method that throws a deeply nested exception
    static void simulateDatabaseOperation() throws ApplicationException {
        throw new BusinessException("Invalid business rule");
    }
}
