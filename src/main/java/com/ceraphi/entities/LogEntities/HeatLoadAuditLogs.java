package com.ceraphi.entities.LogEntities;

import ENUM.OperationType;
import com.ceraphi.entities.MasterDataTables.EstimatedCostOpexDeep;
import com.ceraphi.entities.MasterDataTables.HeatLoadFuels;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HeatLoadAuditLogs")
public class HeatLoadAuditLogs {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "heatLoadFuels_id")
        private HeatLoadFuels heatLoadFuels;

        @ManyToOne
        @JoinColumn(name = "change_set_id")
        private HeatLoadChangesSet changeSet;

        private String fieldName;

        private String oldValue;

        private String newValue;

        private LocalDateTime timestamp;
        @Enumerated(EnumType.STRING)
        @Column(name = "operation_type")
        private OperationType operationType;


    }

