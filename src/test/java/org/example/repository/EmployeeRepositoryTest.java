package org.example.repository;

import org.example.entity.Employee;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeRepositoryTest {

    @Before
    public void setup() {
        EmployeeRepository.getInstance().clear();
    }

    @Test
    public void testGetInstance() {
        EmployeeRepository repo1 = EmployeeRepository.getInstance();
        EmployeeRepository repo2 = EmployeeRepository.getInstance();
        assertSame(repo1, repo2, "EmployeeRepository should be a singleton");
    }

    @Test
    public void testGetCeoNotNull() {
        EmployeeRepository repository = EmployeeRepository.getInstance();
        assertThrowsExactly(IllegalStateException.class, repository::getCeo);
    }

    @Test
    public void testFindEmployeeNotFound() {
        EmployeeRepository repository = EmployeeRepository.getInstance();
        assertNull(repository.findEmployee(9999), "Finding non-existing employee should return null");
    }

    @Test
    public void testEmployeeAddition() {
        EmployeeRepository repository = EmployeeRepository.getInstance();

        var emp = new Employee(1, "Test", "Employee", (double) 1000);
        repository.addEmployee(emp);

        var foundEmp = repository.findEmployee(1);
        assertNotNull(foundEmp, "Added employee should be found");
        assertEquals("Test Employee", foundEmp.getName(), "Employee name should match");
    }

    @Test
    public void testDetermineCEO() {
        EmployeeRepository repository = EmployeeRepository.getInstance();
        var emp1 = new Employee(1, "CEO", "Leader", (double) 5000);
        var emp2 = new Employee(2, "Manager", "Worker", (double) 3000);
        repository.addEmployee(emp1);
        repository.addEmployee(emp2);
        emp2.reportTo(emp1);

        repository.determineCEO();
        var ceo = repository.getCeo();
        assertEquals(emp1, ceo, "The determined CEO should be the top-level employee");
    }

}