package com.company.dashboard.controller;
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
import org.springframework.web.bind.annotation.RestController;

import com.company.dashboard.model.Dept;
import com.company.dashboard.service.DeptService;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DeptController {

    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @PostMapping
    public ResponseEntity<Dept> createDept(@RequestBody Dept dept) {
        return ResponseEntity.ok(deptService.createDept(dept));
    }

    @GetMapping
    public ResponseEntity<List<Dept>> getAllDepts() {
        return ResponseEntity.ok(deptService.getAllDepts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dept> getDeptById(@PathVariable Integer id) {
        Dept dept = deptService.getDeptById(id);
        return (dept != null) ? ResponseEntity.ok(dept) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Dept> updateDept(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        Dept updatedDept = deptService.updateDept(id, updates);
        return (updatedDept != null) ? ResponseEntity.ok(updatedDept) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDept(@PathVariable Integer id) {
        boolean deleted = deptService.deleteDept(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/test/{id}")
    public ResponseEntity<String> test(@PathVariable Integer id) {
        return ResponseEntity.ok("Reached controller with ID = " + id);
    }

}
