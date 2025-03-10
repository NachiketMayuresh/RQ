package com.reliaquest.api;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiApplicationTest {

    private static List<String> idList = new ArrayList<>();

    @Autowired
    EmployeeService employeeService;

    @Test
    void someTest() {
        insert();
        getAll();
        getById();
        getByIdFailure();
        getByName();
        getByNameFailure();
        getHighestSalary();
        getTopTenHighestEarningNames();
        deleteById();
        deleteByIdFailure();
    }

    @Test
    void insert() {
        int salary = 100000;
        int age = 25;
        for (int i = 1; i < 10; i++) {
            salary = salary + 100;
            age = age + 1;
            Employee employee = new Employee(null, "TestName".concat(String.valueOf(i)), salary, age, "TestTitle ".concat(String.valueOf(i)), "TestName".concat(String.valueOf(i)).concat("@company.com"));
            Employee responseEmployee = employeeService.createEmployee(employee);
            assertThat(responseEmployee.getId()).isNotNull();
            idList.add(responseEmployee.getId().toString());
        }
    }

    @Test
    void getAll() {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        assertThat(allEmployees).isNotEmpty();
    }

    @Test
    void getByName() {
        List<Employee> allEmployees = employeeService.getEmployeesByNameSearch("Name");
        assertThat(allEmployees).isNotEmpty();
    }

    @Test
    void getByNameFailure() {
        List<Employee> allEmployees = employeeService.getEmployeesByNameSearch("Nachiket");
        assertThat(allEmployees).isEmpty();
    }

    @Test
    void getById() {
        Employee employee = employeeService.getEmployeeById(idList.get(1));
        assertThat(employee).isNotNull();
    }

    @Test
    void getByIdFailure() {
        Employee employee = employeeService.getEmployeeById("incorrect");
        assertThat(employee).isNull();
    }

    @Test
    void getHighestSalary() {
        Integer salary = employeeService.getHighestSalaryOfEmployees();
        assertThat(salary).isNotEqualTo(0);
    }

    @Test
    void getTopTenHighestEarningNames() {
        List<String> employeeNames = employeeService.getTopTenHighestEarningEmployeeNames();
        assertThat(employeeNames).isNotEmpty();
    }

    @Test
    void deleteById() {
        String employeeName = employeeService.deleteEmployeeById(idList.get(1));
        assertThat(employeeName).isNotEmpty();
    }

    @Test
    void deleteByIdFailure() {
        String employeeName = employeeService.deleteEmployeeById("incorrect");
        assertThat(employeeName).isEmpty();
    }

}
