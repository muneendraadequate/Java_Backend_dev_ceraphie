package com.ceraphi.utils.UkWells;

import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.WellDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UkWells {
    private List<WellDetails> wellsList;
}
