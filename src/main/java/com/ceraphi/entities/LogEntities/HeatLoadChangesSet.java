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
@Table(name = "HeatLoadChangesSet")
public class HeatLoadChangesSet {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private LocalDateTime timestamp;
        @Lob
        private String comment;
        @OneToMany(mappedBy = "changeSet", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<HeatLoadAuditLogs> capexDeepAuditLogs = new ArrayList<>();
    }

