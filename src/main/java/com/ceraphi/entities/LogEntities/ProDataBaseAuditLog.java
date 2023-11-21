package com.ceraphi.entities.LogEntities;

import ENUM.OperationType;
import com.ceraphi.entities.MasterDataTables.ProDataBaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prodata_audit_log")
public class ProDataBaseAuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prodata_id")
    private ProDataBaseModel prodata;

    @ManyToOne
    @JoinColumn(name = "change_set_id")
    private ChangeSet changeSet;

    private String fieldName;

    private String oldValue;

    private String newValue;

    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;
}
