package org.example.service;

import org.example.entity.Employee;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizationAnalyzer {

    private static final double MIN_SALARY_MULTIPLIER = 1.2;
    private static final double MAX_SALARY_MULTIPLIER = 1.5;
    private static final int DEFAULT_MAX_DEPTH = 4;

    public record SalaryAnalysisResult(boolean hasIssues, List<String> issues) {
    }

    public record ReportingLineResult(boolean hasIssues, List<String> issues) {
    }

    public SalaryAnalysisResult checkManagerSalaries(@NotNull Employee ceo) {
        Map<Employee, String> salaryIssues = new HashMap<>();
        dfsSalaryCheck(ceo, salaryIssues);

        List<String> issues = new ArrayList<>(salaryIssues.values());
        return new SalaryAnalysisResult(!issues.isEmpty(), issues);
    }

    public ReportingLineResult checkReportingLine(@NotNull Employee ceo) {
        Map<Employee, String> reportingLineIssues = new HashMap<>();
        checkDepth(ceo, 1, reportingLineIssues);

        List<String> issues = new ArrayList<>(reportingLineIssues.values());
        return new ReportingLineResult(!reportingLineIssues.isEmpty(), issues);
    }

    private void dfsSalaryCheck(@NotNull Employee manager, Map<Employee, String> salaryIssues) {
        if (manager.getSubordinates().isEmpty()) return;

        double avgSubSalary = manager.getSubordinates().stream().mapToDouble(Employee::getSalary).average().orElse(0);

        double minRequired = avgSubSalary * MIN_SALARY_MULTIPLIER;
        double maxAllowed = avgSubSalary * MAX_SALARY_MULTIPLIER;

        if (manager.getSalary() < minRequired) {
            double diff = minRequired - manager.getSalary();
            salaryIssues.put(manager, String.format("%s earns %.2f LESS than minimum required (%.2f)", manager.getName(), diff, minRequired));
        } else if (manager.getSalary() > maxAllowed) {
            double diff = manager.getSalary() - maxAllowed;
            salaryIssues.put(manager, String.format("%s earns %.2f MORE than maximum allowed (%.2f)", manager.getName(), diff, maxAllowed));
        }

        for (Employee e : manager.getSubordinates()) {
            dfsSalaryCheck(e, salaryIssues);
        }
    }

    private void checkDepth(Employee employee, int depth, Map<Employee, String> reportingLineIssues) {

        if (depth > DEFAULT_MAX_DEPTH) {
            reportingLineIssues.put(employee, String.format("%s has reporting line depth of %d, exceeding maximum allowed %d", employee.getName(), depth, DEFAULT_MAX_DEPTH));
        }

        for (Employee sub : employee.getSubordinates()) {
            checkDepth(sub, depth + 1, reportingLineIssues);
        }
    }

}
