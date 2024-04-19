package com.tus.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

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

	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}


	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employee.setName(employeeDetails.getName());
		employee.setEmailId(employeeDetails.getEmailId());
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	public EmployeeResp getEmployeeById(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		Department dpt = getDepartmentByName(employee.getDepartment());
		EmployeeResp er = deptMapper(dpt, employee);
		return er;
	}

    public Department getDepartmentByName(String name) {
        String url = BASE_URL + "deptName/" + name;
        ResponseEntity<Department> response = restTemplate.getForEntity(url, Department.class);
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

}
