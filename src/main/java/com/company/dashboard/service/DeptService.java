package com.company.dashboard.service;

import java.util.List;
import java.util.Map;

import com.company.dashboard.model.Dept;

public interface DeptService {
    Dept createDept(Dept dept);
    List<Dept> getAllDepts();
    // READ BY ID
    Dept getDeptById(Integer id);

    // UPDATE (PATCH)
    Dept updateDept(Integer id, Map<String, Object> updates);

    // DELETE
    boolean deleteDept(Integer id);
}
