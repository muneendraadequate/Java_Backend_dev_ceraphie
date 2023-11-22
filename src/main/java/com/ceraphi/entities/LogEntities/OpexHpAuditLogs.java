package com.ceraphi.entities.LogEntities;

import com.ceraphi.utils.ENUM.OperationType;
import com.ceraphi.entities.MasterDataTables.EstimatedCostOpexHP;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OpexHpAuditLogs")
public class OpexHpAuditLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CostOpexHp_id")
    private EstimatedCostOpexHP CostCapexHp;

    @ManyToOne
    @JoinColumn(name = "change_set_id")
    private OpexHpChangesSet changeSet;

    private String fieldName;

    private String oldValue;

    private String newValue;

    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;


}
