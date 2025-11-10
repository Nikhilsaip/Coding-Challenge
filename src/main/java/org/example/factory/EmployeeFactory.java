package org.example.factory;

import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.jetbrains.annotations.NotNull;

public class EmployeeFactory {

    public @NotNull Employee createEmployeeBasic(int id, @NotNull String firstName, @NotNull String lastName, Double salary) {
        Employee employee = new Employee(id, firstName, lastName, salary);
        EmployeeRepository.getInstance().addEmployee(employee);
        return employee;
    }

    public void linkManager(@NotNull Employee employee, int managerId) {
        Employee manager = EmployeeRepository.getInstance().findEmployee(managerId);
        if (manager != null) {
            employee.reportTo(manager);
        }
    }

}
