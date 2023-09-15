package com.ceraphi.services;

import com.ceraphi.dto.SubSurfaceDto;
import com.ceraphi.dto.SurfaceEquipmentDto;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.SubSurface;
import com.ceraphi.utils.ApiResponseData;

public interface SubSurfaceService {
    ApiResponseData<SubSurfaceDto> saveSubSurfaceData(SubSurfaceDto subSurface);
    ApiResponseData<SubSurfaceDto> updateSubSurface(long id,SubSurfaceDto subSurfaceDto);
    ApiResponseData<SubSurfaceDto> getSubSurfaceData(Long id);
    void deleteByGeneralInformationId(Long id);
}
