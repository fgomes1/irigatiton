package br.edu.utfpr.irigation.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "irrigation_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IrrigationLog extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "user_id")
    private String userId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
