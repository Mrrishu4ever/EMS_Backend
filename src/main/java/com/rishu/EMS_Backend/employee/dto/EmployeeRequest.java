package com.rishu.EMS_Backend.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Email @Size(max = 150) String email,
        @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "must be a valid phone number") String phone,
        @NotNull @PastOrPresent LocalDate hireDate,
        @NotNull @Positive BigDecimal salary,
        @NotNull Long departmentId
) {
}