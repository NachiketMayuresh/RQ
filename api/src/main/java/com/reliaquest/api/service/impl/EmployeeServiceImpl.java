package com.reliaquest.api.service.impl;

import com.reliaquest.api.http.HTTPClient;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * EmployeeService impl uses Employee bean as input an output.
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    HTTPClient httpClient;

    @Override
    public List<Employee> getAllEmployees() {
        return httpClient.getAllEmployees();
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        List<Employee> employeeList = httpClient.getAllEmployees();
        List<Employee> filteredList = new ArrayList<>();
        if (employeeList != null && !employeeList.isEmpty()) {
            filteredList.addAll(employeeList.stream().filter(employee -> employee.getName().contains(searchString)).toList());
        }
        log.debug("Searched employee list: {}", filteredList);
        return filteredList;
    }

    @Override
    public Employee getEmployeeById(String id) {
        return httpClient.getEmployee(id);
    }

    @Override
    public Integer getHighestSalaryOfEmployees() {
        List<Employee> employeeList = httpClient.getAllEmployees();
        Integer highestSalary = 0;
        if (employeeList != null && !employeeList.isEmpty()) {
            highestSalary = employeeList.stream().max((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary())).get().getSalary();
        }
        log.debug("Highest salary: {}", highestSalary);
        return highestSalary;
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        List<Employee> employeeList = httpClient.getAllEmployees();
        List<String> employeeNames = new ArrayList<>();
        if (employeeList != null && !employeeList.isEmpty()) {
            employeeNames.addAll(employeeList.stream().sorted(Comparator.comparingInt(Employee::getSalary).reversed()).limit(10).map(Employee::getName).toList());
        }
        log.debug("Top 10 highest earning employee names: {}", employeeNames);
        return employeeNames;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return httpClient.createEmployee(employee);
    }

    @Override
    public String deleteEmployeeById(String id) {
        List<Employee> employeeList = httpClient.getAllEmployees();
        String employeeName = "";
        Optional<Employee> employeeOptional = employeeList.stream().filter(employee -> employee.getId().toString().equals(id)).findFirst();
        if (employeeOptional.isPresent()) {
            employeeName = employeeOptional.get().getName();
            httpClient.deleteEmployee(employeeName);
        }
        log.debug("Deleted employee with name: {}.", employeeName);
        return employeeName;
    }
}
