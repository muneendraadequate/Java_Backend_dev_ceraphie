package com.ceraphi.utils.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WellByType {
    private String type;
    private long value;
}
