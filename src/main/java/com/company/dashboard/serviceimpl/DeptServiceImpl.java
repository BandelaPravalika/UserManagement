package com.company.dashboard.serviceimpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.dashboard.model.Dept;
import com.company.dashboard.repository.DeptRepository;
import com.company.dashboard.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {

    private final DeptRepository deptRepository;

    public DeptServiceImpl(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    @Override
    public Dept createDept(Dept dept) {
        if (deptRepository.existsByDeptName(dept.getDeptName())) {
            throw new RuntimeException("Department name already exists");
        }
        if (deptRepository.existsByDeptCode(dept.getDeptCode())) {
            throw new RuntimeException("Department code already exists");
        }
        return deptRepository.save(dept);
    }

    @Override
    public List<Dept> getAllDepts() {
        return deptRepository.findAll();
    }
    // READ BY ID
    @Override
    public Dept getDeptById(Integer id) {
        return deptRepository.findById(id).orElse(null);
    }

    // UPDATE (PATCH)
//    @Override
    public Dept updateDept(Integer id, Map<String, Object> updates) {
        Dept dept = deptRepository.findById(id).orElse(null);
        if (dept == null) return null;

        updates.forEach((key, value) -> {
            switch (key) {
                case "deptName":
                    if (value != null && !value.equals(dept.getDeptName())) {
                        if (deptRepository.existsByDeptName(value.toString())) {
                            throw new RuntimeException("Department name already exists");
                        }
                        dept.setDeptName(value.toString());
                    }
                    break;
                case "deptCode":
                    if (value != null && !value.equals(dept.getDeptCode())) {
                        if (deptRepository.existsByDeptCode(value.toString())) {
                            throw new RuntimeException("Department code already exists");
                        }
                        dept.setDeptCode(value.toString());
                    }
                    break;
            }
        });

        return deptRepository.save(dept);
    }



    // DELETE
    @Override
    public boolean deleteDept(Integer id) {
        if (deptRepository.existsById(id)) {
            deptRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
}
