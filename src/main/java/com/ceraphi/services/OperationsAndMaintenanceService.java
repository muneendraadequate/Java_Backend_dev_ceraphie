package com.ceraphi.services;

import com.ceraphi.dto.OperationsAndMaintenanceDto;
import com.ceraphi.utils.ApiResponseData;

public interface OperationsAndMaintenanceService {
    ApiResponseData<OperationsAndMaintenanceDto> saveTheOperationsAndMaintenance(OperationsAndMaintenanceDto operationsAndMaintenanceDto);
    ApiResponseData<OperationsAndMaintenanceDto> updateOperationsAndMaintenance(long id ,OperationsAndMaintenanceDto operationsAndMaintenanceDto);
    ApiResponseData<OperationsAndMaintenanceDto> getOperationsDataById(Long id);
    void deleteByGeneralInformationId(Long id);
}
