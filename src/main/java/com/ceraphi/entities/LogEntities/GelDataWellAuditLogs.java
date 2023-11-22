package com.ceraphi.entities.LogEntities;

import com.ceraphi.utils.ENUM.OperationType;
import com.ceraphi.entities.MasterDataTables.GelDataWell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GelDataAuditLogs")
public class GelDataWellAuditLogs {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "gelDataWell_id")
        private GelDataWell gelDataWell;

        @ManyToOne
        @JoinColumn(name = "change_set_id")
        private GelDataWellChangesSet changeSet;

        private String fieldName;

        private String oldValue;

        private String newValue;

        private LocalDateTime timestamp;
        @Enumerated(EnumType.STRING)
        @Column(name = "operation_type")
        private OperationType operationType;


    }


