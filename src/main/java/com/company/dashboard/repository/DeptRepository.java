package com.company.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.dashboard.model.Dept;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {
    boolean existsByDeptName(String deptName);
    boolean existsByDeptCode(String deptCode);
}
