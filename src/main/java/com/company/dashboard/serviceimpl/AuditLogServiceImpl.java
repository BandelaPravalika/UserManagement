package com.company.dashboard.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.dashboard.model.AuditLog;
import com.company.dashboard.model.AuditLog.Action;
import com.company.dashboard.repository.AuditLogRepository;
import com.company.dashboard.response.ApiResponse;
import com.company.dashboard.service.AuditLogService;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository repository;

    public AuditLogServiceImpl(AuditLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public ApiResponse<List<AuditLog>> getAllLogs() {
        List<AuditLog> logs = repository.findAll();
        return ApiResponse.success("All audit logs fetched", logs);
    }

    @Override
    public ApiResponse<AuditLog> getLogById(Long auditId) {
        AuditLog log = repository.findById(auditId)
                .orElseThrow(() -> new RuntimeException("Audit log not found with id: " + auditId));
        return ApiResponse.success("Audit log fetched", log);
    }

    @Override
    public ApiResponse<List<AuditLog>> getLogsByTableAndRecord(String tableName, Long recordId) {
        List<AuditLog> logs = repository.findByTableNameAndRecordIdOrderByPerformedAtDesc(tableName, recordId);
        return ApiResponse.success("Logs fetched for table and record", logs);
    }

    @Override
    public ApiResponse<List<AuditLog>> getLogsByUser(String performedBy) {
        List<AuditLog> logs = repository.findByPerformedByOrderByPerformedAtDesc(performedBy);
        return ApiResponse.success("Logs fetched for user", logs);
    }

    @Override
    public ApiResponse<List<AuditLog>> getLogsByAction(Action action) {
        List<AuditLog> logs = repository.findByAction(action);
        return ApiResponse.success("Logs fetched by action", logs);
    }

    @Override
    public ApiResponse<List<AuditLog>> getLogsFiltered(String tableName, Action action, String performedBy, LocalDateTime start, LocalDateTime end) {
        List<AuditLog> logs = repository.findByTableNameAndActionAndPerformedByAndPerformedAtBetween(
                tableName, action, performedBy, start, end
        );
        return ApiResponse.success("Filtered logs fetched", logs);
    }
}
