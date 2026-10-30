package com.rishu.EMS_Backend.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    @Override
    @EntityGraph(attributePaths = "department")
    Page<Employee> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "department")
    Optional<Employee> findWithDepartmentById(Long id);
}