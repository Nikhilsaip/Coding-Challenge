package org.example.util;

import org.example.entity.Employee;
import org.example.factory.EmployeeFactory;
import org.example.repository.EmployeeRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvReader {

    public static void readEmployeesFromCsv(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            EmployeeFactory employeeFactory = new EmployeeFactory();
            Map<Employee, Integer> managerMap = new HashMap<>();
            while ((line = br.readLine()) != null) {
                // csv format Id,firstName,lastName,salary,managerId?

                String[] data = line.split(",");
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].replace("\"", "").trim();
                }
                int id = Integer.parseInt(data[0].trim());
                String firstName = data[1].trim();
                String lastName = data[2].trim();
                Double salary = Double.parseDouble(data[3].trim());

                Employee employee = employeeFactory.createEmployeeBasic(id, firstName, lastName, salary);
                if (data.length == 5 && !data[4].isBlank()) {
                    managerMap.put(employee, Integer.parseInt(data[4].trim()));
                }
            }
            for (Map.Entry<Employee, Integer> entry : managerMap.entrySet()) {
                employeeFactory.linkManager(entry.getKey(), entry.getValue());
            }
            EmployeeRepository.getInstance().determineCEO();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}