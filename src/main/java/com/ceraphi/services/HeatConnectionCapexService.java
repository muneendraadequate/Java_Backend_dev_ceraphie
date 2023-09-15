package com.ceraphi.services;

import com.ceraphi.dto.HeatConnectionCapexDto;
import com.ceraphi.utils.ApiResponseData;

public interface HeatConnectionCapexService {
    ApiResponseData<HeatConnectionCapexDto> saveHeatConnectionCapex(HeatConnectionCapexDto heatConnectionCapexDto);
    ApiResponseData<HeatConnectionCapexDto> updateHeatConnection(long id,HeatConnectionCapexDto heatConnectionCapexDto);
    ApiResponseData<HeatConnectionCapexDto>  getHeatDataWithId(Long id);
     void deleteByGeneralInformationId(Long id);
}
