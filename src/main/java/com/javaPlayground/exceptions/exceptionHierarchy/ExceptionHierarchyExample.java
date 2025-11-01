package com.javaPlayground.exceptions.exceptionHierarchy;

import com.javaPlayground.exceptions.exceptionHierarchy.exceptions.AppException;
import com.javaPlayground.exceptions.exceptionHierarchy.exceptions.ConnectionException;
import com.javaPlayground.exceptions.exceptionHierarchy.exceptions.DatabaseException;
import org.hibernate.QueryException;

public class ExceptionHierarchyExample {

    public static void main(String[] args) {
        try {
            throw new DatabaseException("Generic database error");
        } catch (ConnectionException e) {
            System.out.println("Caught ConnectionException: " + e.getMessage());
        } catch (QueryException e) {
            System.out.println("Caught QueryException: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Caught DatabaseException: " + e.getMessage());
        } catch (AppException e) {
            System.out.println("Caught AppException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
    }

}
