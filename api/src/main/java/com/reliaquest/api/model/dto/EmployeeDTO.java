package com.reliaquest.api.model.dto;

import com.reliaquest.api.model.Employee;
import lombok.*;

import java.util.UUID;

/**
 * Employee DTO.
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class EmployeeDTO {
    private UUID id;
    private String name;
    private Integer salary;
    private Integer age;
    private String title;
    private String email;

    public static EmployeeDTO from(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .name(employee.getName())
                .salary(employee.getSalary())
                .age(employee.getAge())
                .title(employee.getTitle())
                .build();
    }
}
