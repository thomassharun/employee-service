package com.tus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
