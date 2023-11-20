package com.ceraphi.entities.MasterDataTables;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "change_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pro_data_base_id")
    private ProDataBaseModel proDataBaseModel;

    private String fieldName;
    private String oldValue;
    private String newValue;
    private LocalDateTime changeDateTime;

    // Constructors, getters, and setters
}
