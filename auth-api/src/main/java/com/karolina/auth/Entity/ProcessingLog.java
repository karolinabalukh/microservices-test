package com.karolina.auth.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "processing_log")
public class ProcessingLog {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;
    private String inputText;
    private String outputText;
    private LocalDateTime createdAt;
}
