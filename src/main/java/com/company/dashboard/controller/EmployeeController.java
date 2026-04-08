package com.company.dashboard.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.dashboard.model.Employee;
import com.company.dashboard.response.EmployeeWithOnboardingDTO;
import com.company.dashboard.service.EmailService;
import com.company.dashboard.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")

public class EmployeeController {

	private final EmployeeService employeeService;
	private final EmailService emailService;

	public EmployeeController(EmployeeService employeeService, EmailService emailService) {
		this.employeeService = employeeService;
		this.emailService = emailService;
	}

//	@PostMapping(value="/employees")
	@PostMapping
	public ResponseEntity<Employee> create(@RequestBody @Valid Employee employee) {
		Employee created = employeeService.createEmployee(employee);
		return ResponseEntity.created(URI.create("/api/employees/" + created.getId())).body(created);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeWithOnboardingDTO> getEmployee(@PathVariable Long id) {
		EmployeeWithOnboardingDTO dto = employeeService.getCompleteEmployee(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getAll() {
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
		return ResponseEntity.ok(employeeService.patchEmployee(id, updates));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deactivateEmployee(@PathVariable Long id) {
		employeeService.deactivateEmployee(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/verify")
	public ResponseEntity<String> verifyToken(@RequestParam String token) {
		return ResponseEntity.ok(employeeService.verifyToken(token));
	}

	@GetMapping("/test-mail")
	public String testMail() {
		emailService.sendMail("differentgmail@gmail.com", "Test Mail 2", "Second test");
		return "Mail sent";
	}

	@PostMapping("/ping")
	public ResponseEntity<String> ping(@RequestBody Map<String, Object> body) {
		System.out.println(body);
		return ResponseEntity.ok("Ping received");
	}

	@PatchMapping("/{id}/activate")
	public ResponseEntity<Employee> activateEmployee(@PathVariable Long id) {
		Employee updated = employeeService.updateEmployeeStatusToActive(id);
		return ResponseEntity.ok(updated);
	}

}

