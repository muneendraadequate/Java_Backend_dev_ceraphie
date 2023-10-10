package com.ceraphi.utils.summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SummaryResponse {

    private SummaryHeatPump summaryHeatPump;
    private SummaryDeepWell summaryDeepWell;
}
