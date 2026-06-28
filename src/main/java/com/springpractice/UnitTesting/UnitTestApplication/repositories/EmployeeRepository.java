package com.springpractice.UnitTesting.UnitTestApplication.repositories;

import com.springpractice.UnitTesting.UnitTestApplication.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByEmail(String email);
}
