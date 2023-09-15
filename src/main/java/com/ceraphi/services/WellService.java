package com.ceraphi.services;

import com.ceraphi.dto.WellInfoDto;
import com.ceraphi.entities.WellInformation;
import com.ceraphi.utils.ApiResponseData;

import java.util.List;

public interface WellService {
    ApiResponseData<WellInfoDto> saveWellInformation(Long generalInfo_id, WellInfoDto wellInformation);

   ApiResponseData<List<WellInfoDto>> getWellInformationByGeneralInformationId(long id);
//     void deleteWellDetail(Long wellInformationId, Long wellDetailId);
ApiResponseData<WellInfoDto> updateWellsData(long id,WellInfoDto wellInfoDto);
    void deleteByGeneralInformationId(Long id);
}
