package com.company.dashboard.service;

import java.time.LocalDateTime;
import java.util.List;

import com.company.dashboard.model.AuditLog;
import com.company.dashboard.response.ApiResponse;

public interface AuditLogService {

    ApiResponse<List<AuditLog>> getAllLogs();

    ApiResponse<AuditLog> getLogById(Long auditId);

    ApiResponse<List<AuditLog>> getLogsByTableAndRecord(String tableName, Long recordId);

    ApiResponse<List<AuditLog>> getLogsByUser(String performedBy);

    ApiResponse<List<AuditLog>> getLogsByAction(AuditLog.Action action);

    ApiResponse<List<AuditLog>> getLogsFiltered(String tableName,
                                                AuditLog.Action action,
                                                String performedBy,
                                                LocalDateTime start,
                                                LocalDateTime end);
}
