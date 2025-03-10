package com.reliaquest.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.reliaquest.api.model.dto.EmployeeDTO;
import lombok.*;

import java.util.UUID;

/**
 * Employee model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Employee {

    private UUID id;

    @JsonAlias({"name", "employee_name"})
    private String name;

    @JsonAlias({"salary", "employee_salary"})
    private Integer salary;

    @JsonAlias({"age", "employee_age"})
    private Integer age;

    @JsonAlias({"title", "employee_title"})
    private String title;

    @JsonAlias({"email", "employee_email"})
    private String email;

    public static Employee from(EmployeeDTO employeeDTO) {
        return Employee.builder()
                .id(employeeDTO.getId())
                .email(employeeDTO.getEmail())
                .name(employeeDTO.getName())
                .salary(employeeDTO.getSalary())
                .age(employeeDTO.getAge())
                .title(employeeDTO.getTitle())
                .build();
    }
}
