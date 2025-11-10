# Development Assumptions

## Organizational Hierarchy
1. The organization has a single CEO (employee with no manager)
2. Every employee (except the CEO) has exactly one manager
3. An employee can have multiple subordinates (direct reports)
4. There are no circular reporting relationships allowed

## Employee Data
Each employee must have:
   - A unique positive integer ID
   - A non-empty first name
   - A non-empty last name
   - A positive salary value (greater than 0)
     
## Salary Rules
1. A manager's salary must be within a specific range relative to their subordinates' average salary:
   - Minimum: 1.2 times the average salary of their direct subordinates
   - Maximum: 1.5 times the average salary of their direct subordinates
2. Salary comparisons are only made between a manager and their direct subordinates
3. Managers with no subordinates are not subject to salary validation rules

## Data Input (CSV Format)
1. The CSV file:
   - Contains a header row
   - Uses comma as the delimiter
   - Has fields in the order: Id,firstName,lastName,salary,managerId
2. The managerId field:
   - Is optional (may be empty)
   - When present, references an existing employee ID
   - Is empty for the CEO
