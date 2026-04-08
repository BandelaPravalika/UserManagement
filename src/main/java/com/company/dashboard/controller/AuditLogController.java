package com.company.dashboard.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.dashboard.model.AuditLog;
import com.company.dashboard.response.ApiResponse;
import com.company.dashboard.service.AuditLogService;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public ApiResponse<List<AuditLog>> getAllLogs() {
        return auditLogService.getAllLogs();
    }

    @GetMapping("/{id}")
    public ApiResponse<AuditLog> getLogById(@PathVariable Long id) {
        return auditLogService.getLogById(id);
    }

    @GetMapping("/table/{tableName}/record/{recordId}")
    public ApiResponse<List<AuditLog>> getLogsByTableAndRecord(@PathVariable String tableName, @PathVariable Long recordId) {
        return auditLogService.getLogsByTableAndRecord(tableName, recordId);
    }

    @GetMapping("/user/{performedBy}")
    public ApiResponse<List<AuditLog>> getLogsByUser(@PathVariable String performedBy) {
        return auditLogService.getLogsByUser(performedBy);
    }

    @GetMapping("/action/{action}")
    public ApiResponse<List<AuditLog>> getLogsByAction(@PathVariable AuditLog.Action action) {
        return auditLogService.getLogsByAction(action);
    }

    @GetMapping("/filter")
    public ApiResponse<List<AuditLog>> getLogsFiltered(
            @RequestParam String tableName,
            @RequestParam AuditLog.Action action,
            @RequestParam String performedBy,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return auditLogService.getLogsFiltered(tableName, action, performedBy, start, end);
    }
}

