package org.example.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Employee {
    private final int id;
    @NotNull
    private String firstName, lastName;
    @NotNull
    private Double salary;

    @Nullable
    private Employee manager; // null if CEO

    @NotNull
    private final List<Employee> subordinates;

    public Employee(int id, @NotNull String firstName, @NotNull String lastName, @NotNull Double salary) {
        if (firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.subordinates = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public @NotNull Double getSalary() {
        return salary;
    }

    public @Nullable Employee getManager() {
        return manager;
    }

    public List<Employee> getSubordinates() {
        return Collections.unmodifiableList(subordinates);
    }

    public void reportTo(@NotNull Employee manager) {
        if (this.manager != null) {
            this.manager.removeSubordinate(this);
        }
        this.manager = manager;
        manager.addSubordinate(this);
    }

    private void addSubordinate(@NotNull Employee employee) {
        subordinates.add(employee);
    }

    private void removeSubordinate(Employee employeeToRemove) {
        subordinates.removeIf(employee -> employee.getId() == employeeToRemove.getId());
    }
}
