package com.karolina.auth.Repositories;

import com.karolina.auth.Entity.ProcessingLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessingLogRepository extends JpaRepository<ProcessingLog, UUID> {
}