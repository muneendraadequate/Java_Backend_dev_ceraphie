package com.ceraphi.entities.LogEntities;

import com.ceraphi.utils.ENUM.OperationType;
import com.ceraphi.entities.MasterDataTables.EstimatedCostCapexHP;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "capexHP_audit_log")
public class CapexHpAuditLogs {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "CostCapexHP_id")
        private EstimatedCostCapexHP CostCapexHp;

        @ManyToOne
        @JoinColumn(name = "change_set_id")
        private ChangesSetCapexHp changeSet;

        private String fieldName;

        private String oldValue;

        private String newValue;

        private LocalDateTime timestamp;
        @Enumerated(EnumType.STRING)
        @Column(name = "operation_type")
        private OperationType operationType;


    }
