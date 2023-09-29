package com.ceraphi.utils.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WellsByRegion {
        private String country;
        private int newWells;
        private int repurposedWells;
}
