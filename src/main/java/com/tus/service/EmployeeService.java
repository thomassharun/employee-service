package com.tus.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.tus.exception.EmployeeExistenceException;
import com.tus.exception.ResourceNotFoundException;
import com.tus.model.Department;
import com.tus.model.Employee;
import com.tus.model.EmployeeResp;
import com.tus.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	private final String BASE_URL = "http://localhost:8081/api/v1/department/";

	private final RestTemplate restTemplate = new RestTemplate();

	public List<Employee> getAllEmployees() {
		List<Employee> empList = employeeRepository.findAll();
		return empList;

	}
	
	public Employee save(Employee emp) {

		Optional<List<Employee>> empDb = employeeRepository.findByEmailId(emp.getEmailId());

		if (!empDb.isPresent()) {
			throw new EmployeeExistenceException(
					"Employee with email : " + emp.getEmailId() + " already exists.");
		}
		try {
			Employee emp1 = employeeRepository.save(emp);
			return emp1;
		} catch (Exception ex) {
			throw new ResourceNotFoundException(
					"Employee not created");
		}
	}

	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employee.setName(employeeDetails.getName());
		employee.setEmailId(employeeDetails.getEmailId());
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	public EmployeeResp getEmployeeById(Long id) {

		try {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
			Department dpt = getDepartmentByName(employee.getDepartment());
			EmployeeResp er = deptMapper(dpt, employee);
			return er;
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Employee with Id: " + id + " not found");

		}
	}

	public Department getDepartmentByName(String name) {
		String url = BASE_URL + "deptName/" + name;
		// Generate a UUID for correlation ID
		String correlationId = UUID.randomUUID().toString();

		// Set the correlation ID in the request header
		HttpHeaders headers = new HttpHeaders();
		headers.add("tus-correlation-id", correlationId);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		// Send the request with the correlation ID header
		ResponseEntity<Department> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Department.class);

		// ResponseEntity<Department> response =
		// restTemplate.getForEntity(url,Department.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new RuntimeException("Failed to fetch department by name: " + name);
		}
	}

	public EmployeeResp deptMapper(Department dept, Employee emp) {
		EmployeeResp er = new EmployeeResp();
		er.setDepartment(dept);
		er.setEmailId(emp.getEmailId());
		er.setMobile(emp.getMobile());
		er.setName(emp.getName());
		return er;
	}

	public Employee updateEmployee(Employee newEmp, Long id) {
		Employee empDb = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not exist with id :" + id));

		empDb.setName(newEmp.getName());
		empDb.setEmailId(newEmp.getEmailId());
		empDb.setDepartment(newEmp.getDepartment());
		empDb.setMobile(newEmp.getMobile());
		Employee updatedEmp = employeeRepository.save(empDb);
		return updatedEmp;
	}

	public void deleteEmp(Long id) {
		Employee department = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee deos not exist with id :" + id));
		employeeRepository.delete(department);
	}

}
