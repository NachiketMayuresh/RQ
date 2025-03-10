package com.reliaquest.api.impl;

import com.reliaquest.api.controller.IEmployeeController;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.dto.EmployeeDTO;
import com.reliaquest.api.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * IEmployeeController impl uses EmployeeDTO as input an output.
 */
@Slf4j
public class IEmployeeControllerImpl implements IEmployeeController {

    @Autowired
    private EmployeeService mockEmployeeService;

    @Override
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        log.debug("Get all employees.");
        List<Employee> employeeList = mockEmployeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOList = employeeList.stream().map(EmployeeDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(employeeDTOList);
    }

    @Override
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByNameSearch(String searchString) {
        log.debug("Search employee by name - {}", searchString);
        List<Employee> employeeList = mockEmployeeService.getEmployeesByNameSearch(searchString);
        List<EmployeeDTO> employeeDTOList = employeeList.stream().map(EmployeeDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(employeeDTOList);
    }

    @Override
    public ResponseEntity<EmployeeDTO> getEmployeeById(String id) {
        log.debug("Get employee by id - {}", id);
        Employee employee = mockEmployeeService.getEmployeeById(id);
        EmployeeDTO employeeDTO = EmployeeDTO.from(employee);
        return ResponseEntity.ok().body(employeeDTO);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        log.debug("Get highest salary of employees.");
        return ResponseEntity.ok().body(mockEmployeeService.getHighestSalaryOfEmployees());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.debug("Get top highest earning employee names.");
        return ResponseEntity.ok().body(mockEmployeeService.getTopTenHighestEarningEmployeeNames());
    }

    @Override
    public ResponseEntity<EmployeeDTO> createEmployee(Object employeeInput) {
        log.debug("Create employee.");
        Employee employee = Employee.from((EmployeeDTO) employeeInput);
        Employee employeeResp = mockEmployeeService.createEmployee(employee);
        EmployeeDTO employeeDTOResp = EmployeeDTO.from(employeeResp);
        return ResponseEntity.ok().body(employeeDTOResp);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        log.debug("Delete employee by id - {}.", id);
        return ResponseEntity.ok().body(mockEmployeeService.deleteEmployeeById(id));
    }
}
