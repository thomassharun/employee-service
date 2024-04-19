package com.tus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.exception.ResourceNotFoundException;
import com.tus.model.Employee;
import com.tus.model.EmployeeResp;
import com.tus.model.TestDetails;
import com.tus.repository.EmployeeRepository;
import com.tus.service.EmployeeService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	private final TestDetails testDetails;
	
	@Autowired
    public EmployeeController(TestDetails testDetails) {
        this.testDetails = testDetails;
    }
	

	// get all employees
	@GetMapping("/employee")
	public List<Employee> getAllEmployees() {
		List<Employee> emplist = employeeRepository.findAll();

		/*
		 * for (Employee emp : emplist) { EmployeeResp emp1 = new EmployeeResp();
		 * emp1.setId(emp.g()); emp1.setName(emp.getName());
		 * emp1.setEmailId(emp.getEmailId()); emp1.setMobile(emp.getMobile());
		 * emp1.setDepartment(emp.getDepartment());
		 * 
		 * emp1List.add(emp1); }
		 */

		return emplist;
	}

	// create employee rest api
	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	// get employee by id rest api
	@GetMapping("/employee/{id}")
	public ResponseEntity<EmployeeResp> getEmployeeById(@PathVariable Long id) {
		EmployeeResp employee = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(employee);
	}

	// update employee rest api
	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employee.setName(employeeDetails.getName());
		employee.setEmailId(employeeDetails.getEmailId());

		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	// delete employee rest api
	@DeleteMapping("/menu/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	// get contact-info rest api
	@GetMapping("/contact-info")
	public ResponseEntity<TestDetails> contact() {
		return ResponseEntity.ok(testDetails);
	}

}
