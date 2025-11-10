package org.example.service;


import org.example.entity.Employee;
import org.example.factory.EmployeeFactory;
import org.example.repository.EmployeeRepository;
import org.example.service.OrganizationAnalyzer.ReportingLineResult;
import org.example.service.OrganizationAnalyzer.SalaryAnalysisResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class OrganizationAnalyzerTest {
    EmployeeFactory factory;
    OrganizationAnalyzer analyzer;

    @Before
    public void setup() {
        factory = new EmployeeFactory();
        EmployeeRepository.getInstance().clear();
        analyzer = new OrganizationAnalyzer();
    }

    @Test
    public void checkManagerSalaries() {
        Employee ceo = factory.createEmployeeBasic(1, "Alice", "CEO", 15000.0);
        Employee manager1 = factory.createEmployeeBasic(2, "Bob", "Manager", 800.00);
        Employee emp1 = factory.createEmployeeBasic(3, "Charlie", "Employee", 500.00);
        Employee emp2 = factory.createEmployeeBasic(4, "David", "Employee", 1000.00);
        manager1.reportTo(ceo);
        emp1.reportTo(manager1);
        emp2.reportTo(manager1);
        EmployeeRepository.getInstance().determineCEO();
        SalaryAnalysisResult result = analyzer.checkManagerSalaries(ceo);
        assertTrue(result.hasIssues(), "Expected salary issues due to low manager salary");
        Assert.assertEquals(
                List.of(
                        "Alice CEO earns 13800.00 MORE than maximum allowed (1200.00)",
                        "Bob Manager earns 100.00 LESS than minimum required (900.00)"),
                result.issues().stream().sorted().toList());
    }

    @Test
    public void checkReportingLine() {
        Employee ceo = factory.createEmployeeBasic(1, "Alice", "CEO", 15000.0);
        Employee manager1 = factory.createEmployeeBasic(2, "Bob", "Manager", 12000.0);
        Employee manager2 = factory.createEmployeeBasic(3, "Charlie", "Manager", 10000.0);
        Employee manager3 = factory.createEmployeeBasic(4, "David", "Manager", 8000.0);
        Employee emp1 = factory.createEmployeeBasic(4, "David", "Employee", 5000.0);
        emp1.reportTo(manager3);
        manager3.reportTo(manager2);
        manager2.reportTo(manager1);
        manager1.reportTo(ceo);
        EmployeeRepository.getInstance().determineCEO();
        ReportingLineResult result = analyzer.checkReportingLine(ceo);
        assertTrue(result.hasIssues(), "Expected reporting line depth issues");
        Assert.assertEquals("David Employee has reporting line depth of 5, exceeding maximum allowed 4", result.issues().getFirst());
    }
}