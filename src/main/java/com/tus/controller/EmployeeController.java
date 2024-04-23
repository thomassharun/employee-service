package com.tus.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.constants.DeptConstants;
import com.tus.model.Department;
import com.tus.model.Employee;
import com.tus.model.EmployeeResp;
import com.tus.model.ResponseDto;
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
	private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	public EmployeeController(TestDetails testDetails) {
		this.testDetails = testDetails;
	}

	// create employee rest api
	@PostMapping("/employee")
	public ResponseEntity<ResponseDto> createEmployee(@RequestBody Employee employee,
			@RequestHeader("tus-correlation-id") String correlationId) {
		logger.info("Inside createEmployee api with CorrelationId: {} and mail: {}", correlationId,
				employee.getEmailId());
		employeeService.save(employee);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(DeptConstants.STATUS_201, DeptConstants.MESSAGE_201));
	}

	// get employee by id rest api
	@GetMapping("/employee/{id}")
	public ResponseEntity<EmployeeResp> getEmployeeById(@PathVariable Long id,
			@RequestHeader("tus-correlation-id") String correlationId) {
		logger.info("Inside getEmployeeById api with CorrelationId: {} and Id: {}", correlationId, id);
		EmployeeResp employee = employeeService.getEmployeeById(id);
		return ResponseEntity.status(HttpStatus.OK).body(employee);
	}

	// update employee rest api
	@PutMapping("/employee/{id}")
	public ResponseEntity<ResponseDto> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails,
			@RequestHeader("tus-correlation-id") String correlationId) {
		logger.info("Inside updateEmployee api with CorrelationId: {} and mail: {}", correlationId,
				employeeDetails.getEmailId());

		Employee updEmp = employeeService.updateEmployee(employeeDetails, id);
		if (updEmp != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(DeptConstants.STATUS_200, DeptConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(DeptConstants.STATUS_500, DeptConstants.MESSAGE_500));
		}
		// return ResponseEntity.ok(updatedEmployee);
	}

	// delete employee rest api
	@DeleteMapping("/menu/{id}")
	public ResponseEntity<ResponseDto> deleteEmployee(@PathVariable Long id,
			@RequestHeader("tus-correlation-id") String correlationId) {
		logger.info("Inside deleteEmployee api with CorrelationId: {} and Id: {}", correlationId, id);

		try {
			employeeService.deleteEmp(id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(DeptConstants.STATUS_204, DeptConstants.MESSAGE_204));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(DeptConstants.STATUS_500, DeptConstants.MESSAGE_500));
		}
	}

	// get contact-info rest api
	@GetMapping("/contact-info")
	public ResponseEntity<TestDetails> getContactInfo(@RequestHeader("tus-correlation-id") String correlationId) {
		logger.info("Inside getContactInfo api with CorrelationId: {}", correlationId);
		return ResponseEntity.status(HttpStatus.OK).body(testDetails);
	}

	// get all employees
	@GetMapping("/employee")
	public List<Employee> getAllEmployees(@RequestHeader("tus-correlation-id") String correlationId) {
		logger.info("Inside getAllDepartments api with CorrelationId: {}", correlationId);
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

}
