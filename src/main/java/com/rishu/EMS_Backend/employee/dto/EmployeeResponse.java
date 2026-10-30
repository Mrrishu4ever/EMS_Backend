package com.rishu.EMS_Backend.employee.dto;

import com.rishu.EMS_Backend.employee.Employee;
import com.rishu.EMS_Backend.employee.EmployeeStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate hireDate,
        BigDecimal salary,
        EmployeeStatus status,
        Long departmentId,
        String departmentName
) {
    public static EmployeeResponse from(Employee e) {
        return new EmployeeResponse(
                e.getId(), e.getFirstName(), e.getLastName(), e.getEmail(), e.getPhone(),
                e.getHireDate(), e.getSalary(), e.getStatus(),
                e.getDepartment().getId(), e.getDepartment().getName());
    }
}