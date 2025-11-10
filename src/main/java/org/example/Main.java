package org.example;

import org.example.repository.EmployeeRepository;
import org.example.service.OrganizationAnalyzer;
import org.example.util.CsvReader;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path filePath = Path.of("src", "main", "resources", "employees.csv");

        CsvReader.readEmployeesFromCsv(filePath.toString());

        OrganizationAnalyzer analyzer = new OrganizationAnalyzer();

        System.out.println("=== Salary Analysis ===");
        var salaryResult = analyzer.checkManagerSalaries(EmployeeRepository.getInstance().getCeo());
        if (!salaryResult.hasIssues()) {
            System.out.println("All manager salaries are within acceptable ranges.");
        } else {
            salaryResult.issues().forEach(System.out::println);
        }
        System.out.println("=======================\n");

        System.out.println("=== Reporting Line Depth ===");
        var depthResult = analyzer.checkReportingLine(EmployeeRepository.getInstance().getCeo());
        if (!depthResult.hasIssues()) {
            System.out.println("All employees have acceptable reporting line depth.");
        } else {
            depthResult.issues().forEach(System.out::println);
        }
        System.out.println("==========================");
    }
}