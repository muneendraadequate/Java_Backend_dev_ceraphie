package com.ceraphi.entities.LogEntities;

import com.ceraphi.utils.ENUM.OperationType;
import com.ceraphi.entities.MasterDataTables.EstimatedCostCapexDeep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "capexDeep_audit_log")
public class CapexDeepAuditLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CostCapexDeep_id")
    private EstimatedCostCapexDeep CostCapexDeep;

    @ManyToOne
    @JoinColumn(name = "change_set_id")
    private ChangesSetCapexDeep changeSet;

    private String fieldName;

    private String oldValue;

    private String newValue;

    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;


}
