package org.example.entity;

import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void getName() {
        Employee employee = new Employee(123, "Joe", "Doe", (double) 60000);
        assertEquals("Joe Doe", employee.getName());
    }

    @Test
    public void getManager() {
        Employee manager = new Employee(123, "Joe", "Doe", (double) 60000);
        Employee employee = new Employee(124, "Martin", "Chekov", (double) 45000);
        employee.reportTo(manager);
        assertEquals(manager, employee.getManager());
    }

    @Test
    public void getReporters() {
        Employee manager = new Employee(123, "Joe", "Doe", (double) 60000);
        Employee employee = new Employee(124, "Martin", "Chekov", (double) 45000);
        Employee intern = new Employee(125, "Jack", "Neo", (double) 5000);
        employee.reportTo(manager);
        intern.reportTo(manager);

        List<Employee> subordinates = List.of(employee, intern);
        assertEquals(subordinates, manager.getSubordinates());
    }
}