package com.company.dashboard.response;

import java.util.List;

public class ReviewRequest {

    private Long employeeId;
    private String status;
    private String remarks;
    private List<String> rejectedDocuments;

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public List<String> getRejectedDocuments() { return rejectedDocuments; }
    public void setRejectedDocuments(List<String> rejectedDocuments) {
        this.rejectedDocuments = rejectedDocuments;
    }
}
