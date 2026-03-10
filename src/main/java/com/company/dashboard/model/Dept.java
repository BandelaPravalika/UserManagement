package com.company.dashboard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "department",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "dept_name"),
           @UniqueConstraint(columnNames = "dept_code")
       })
public class Dept{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // auto-generated primary key

    @Column(name = "dept_name", nullable = false, unique = true, length = 50)
    private String deptName;

    @Column(name = "dept_code", nullable = false, unique = true, length = 20)
    private String deptCode;

    // Constructors
    public Dept() {}

    public Dept(String deptName, String deptCode) {
        this.deptName = deptName;
        this.deptCode = deptCode;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getDeptCode() { return deptCode; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                ", deptCode='" + deptCode + '\'' +
                '}';
    }
}
