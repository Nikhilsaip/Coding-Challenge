package org.example.repository;

import org.example.entity.Employee;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepository {
    private final Map<Integer, Employee> employeeMap = new HashMap<>();
    private Employee ceo;

    private EmployeeRepository() {
        // single ton
    }

    private static final class EmployeeRepositoryHolder {
        private static final EmployeeRepository instance = new EmployeeRepository();
    }

    public static EmployeeRepository getInstance() {
        return EmployeeRepositoryHolder.instance;
    }

    public synchronized void addEmployee(@NotNull Employee employee) {
        employeeMap.put(employee.getId(), employee);
    }

    public void determineCEO() {
        List<Employee> tops = employeeMap.values().stream().filter(e -> e.getManager() == null).toList();
        if (tops.isEmpty()) {
            throw new IllegalStateException("No CEO found: no employee without manager");
        }
        if (tops.size() > 1) {
            throw new IllegalStateException("Multiple top-level employees found: cannot determine single CEO");
        }
        ceo = tops.getFirst();
    }

    @NotNull
    public Employee getCeo() {
        if (ceo == null) {
            throw new IllegalStateException("Organization has no CEO");
        }
        return ceo;
    }

    @Nullable
    public Employee findEmployee(int id) {
        return employeeMap.get(id);
    }

    public void clear() {
        // for test purpose
        employeeMap.clear();
        ceo = null;
    }
}
