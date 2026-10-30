package com.rishu.EMS_Backend.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rishu.EMS_Backend.common.exception.DuplicateResourceException;
import com.rishu.EMS_Backend.common.exception.ResourceNotFoundException;
import com.rishu.EMS_Backend.department.Department;
import com.rishu.EMS_Backend.department.DepartmentRepository;
import com.rishu.EMS_Backend.employee.dto.EmployeeRequest;
import com.rishu.EMS_Backend.employee.dto.EmployeeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public Page<EmployeeResponse> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(EmployeeResponse::from);
    }

    public EmployeeResponse findById(Long id) {
        return EmployeeResponse.from(getEmployeeOrThrow(id));
    }

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        if (employeeRepository.existsByEmailIgnoreCase(request.email())) {
            throw new DuplicateResourceException("Email already in use: " + request.email());
        }
        Employee employee = new Employee();
        employee.setStatus(EmployeeStatus.ACTIVE);
        apply(employee, request);

        Employee saved = employeeRepository.save(employee);
        log.info("Created employee id={}", saved.getId());
        return EmployeeResponse.from(saved);
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = getEmployeeOrThrow(id);
        if (employeeRepository.existsByEmailIgnoreCaseAndIdNot(request.email(), id)) {
            throw new DuplicateResourceException("Email already in use: " + request.email());
        }
        apply(employee, request);
        return EmployeeResponse.from(employee);
    }

    @Transactional
    public void delete(Long id) {
        Employee employee = getEmployeeOrThrow(id);
        employeeRepository.delete(employee);
        log.info("Deleted employee id={}", id);
    }

    private Employee getEmployeeOrThrow(Long id) {
        return employeeRepository.findWithDepartmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    private void apply(Employee employee, EmployeeRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        employee.setFirstName(request.firstName().trim());
        employee.setLastName(request.lastName().trim());
        employee.setEmail(request.email().trim().toLowerCase());
        employee.setPhone(request.phone());
        employee.setHireDate(request.hireDate());
        employee.setSalary(request.salary());
        employee.setDepartment(department);
    }
}