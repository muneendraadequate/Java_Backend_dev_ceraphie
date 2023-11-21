package com.ceraphi.entities.LogEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GelDataWellChangesSet")
public class GelDataWellChangesSet {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private LocalDateTime timestamp;
        @OneToMany(mappedBy = "changeSet", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<GelDataWellAuditLogs> capexDeepAuditLogs = new ArrayList<>();
    }

