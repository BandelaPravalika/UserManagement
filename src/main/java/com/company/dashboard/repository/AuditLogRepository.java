package com.company.dashboard.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.AuditLog;
import com.company.dashboard.model.AuditLog.Action; // THIS IS THE CORRECT IMPORT

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByTableNameAndRecordIdOrderByPerformedAtDesc(String tableName, Long recordId);

    // Use performedBy string column, not UserId
    List<AuditLog> findByPerformedByOrderByPerformedAtDesc(String performedBy);

    List<AuditLog> findByAction(Action action);

    // Optional: table + action + user + date range
    List<AuditLog> findByTableNameAndActionAndPerformedByAndPerformedAtBetween(
            String tableName,
            Action action,
            String performedBy,
            LocalDateTime start,
            LocalDateTime end
    );
}
