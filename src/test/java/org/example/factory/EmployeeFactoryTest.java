package org.example.factory;

import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeFactoryTest {

    EmployeeFactory factory;

    @Before
    public void setup() {
        factory = new EmployeeFactory();
        EmployeeRepository.getInstance().clear();
    }

    @Test
    public void createEmployeeBasic() {
        Employee employee = factory.createEmployeeBasic(1, "John", "Deo", (double) 1000);
        assertEquals(employee, EmployeeRepository.getInstance().findEmployee(1));
        assertEquals("John Deo", employee.getName());
        assertEquals(1, employee.getId());
    }

    @Test
    public void linkManager() {
        Employee manager = factory.createEmployeeBasic(1, "Jane", "Smith", (double) 2000);
        Employee subordinate = factory.createEmployeeBasic(2, "John", "Deo", (double) 1000);
        factory.linkManager(subordinate, 1);
        assertEquals(manager, subordinate.getManager());
        assertTrue(manager.getSubordinates().contains(subordinate));
    }
}