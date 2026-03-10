//package com.company.dashboard.audit;
//
//import com.company.dashboard.model.AuditLog;
//import com.company.dashboard.repository.AuditLogRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuditListener {
//
//    private static final Logger log = LoggerFactory.getLogger(AuditListener.class);
//
//    @Autowired
//    private AuditLogRepository auditRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @PrePersist
//    public void onCreate(Object entity) {
//        if (shouldSkip(entity)) return;
//
//        AuditLog audit = buildBaseAudit(entity, AuditLog.Action.CREATE);
//        audit.setNewValue(toJson(entity));
//        audit.setOldValue(null);
//
//        saveAuditAsync(audit);
//    }
//
//    @PreUpdate
//    public void onUpdate(Object entity) {
//        if (shouldSkip(entity)) return;
//
//        AuditLog audit = buildBaseAudit(entity, AuditLog.Action.UPDATE);
//        audit.setNewValue(toJson(entity));
//        audit.setOldValue(null);
//
//        saveAuditAsync(audit);
//    }
//
//    @PreRemove
//    public void onDelete(Object entity) {
//        if (shouldSkip(entity)) return;
//
//        AuditLog audit = buildBaseAudit(entity, AuditLog.Action.DELETE);
//        audit.setOldValue(toJson(entity));
//        audit.setNewValue(null);
//
//        saveAuditAsync(audit);
//    }
//
//    private AuditLog buildBaseAudit(Object entity, AuditLog.Action action) {
//        AuditLog audit = new AuditLog();
//        audit.setAction(action);
//
//        Table table = entity.getClass().getAnnotation(Table.class);
//        String tableName = (table != null && !table.name().isEmpty())
//                ? table.name()
//                : entity.getClass().getSimpleName();
//
//        audit.setTableName(tableName);
//
//        try {
//            java.lang.reflect.Field idField = entity.getClass().getDeclaredField("id");
//            idField.setAccessible(true);
//            Long idValue = (Long) idField.get(entity);
//            audit.setRecordId(idValue);
//        } catch (Exception e) {
//            log.warn("Cannot read 'id' field from entity {} — recordId will be null", entity.getClass().getSimpleName());
//            audit.setRecordId(null);
//        }
//
//        audit.setPerformedBy(getCurrentUser());
//        return audit;
//    }
//
//    private String toJson(Object obj) {
//        try {
//            return objectMapper.writeValueAsString(obj);
//        } catch (JsonProcessingException e) {
//            log.error("JSON serialization failed for audit", e);
//            return "{\"error\":\"serialization-failed\"}";
//        }
//    }
//
//    private String getCurrentUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
//            String name = auth.getName();
//            if (!"anonymousUser".equals(name)) {
//                return name;
//            }
//        }
//        return "SYSTEM";
//    }
//
//    private void saveAuditAsync(AuditLog log) {
//        new Thread(() -> {
//            try {
//                auditRepository.save(log);
//            } catch (Exception ex) {
//                AuditListener.log.error("Failed to persist audit log", ex);  // ← use class name
//            }
//        }).start();
//    }
//
//    private boolean shouldSkip(Object entity) {
//        return entity instanceof AuditLog;
//    }
//}