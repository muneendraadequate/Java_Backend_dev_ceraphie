package com.ceraphi.services;

import com.ceraphi.dto.WellInstallationCAPEXDto;
import com.ceraphi.utils.ApiResponseData;

public interface WellInstallationCAPEXService {
    ApiResponseData<?> saveWellInstallationCAPEX(WellInstallationCAPEXDto wellInstallationCAPEXDto);
     void deleteByGeneralInformationId(Long id);
}
