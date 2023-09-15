package com.ceraphi.services;

import com.ceraphi.dto.SurfaceEquipmentDto;
import com.ceraphi.entities.SurfaceEquipment;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.http.ResponseEntity;

public interface SurfaceEquipmentService {
    ApiResponseData<SurfaceEquipmentDto> saveTheSurfaceEquip(SurfaceEquipmentDto surfaceEquip);
    ApiResponseData<SurfaceEquipmentDto> updateSurfaceEquipment(long id,SurfaceEquipmentDto surfaceEquipmentDto);
    ApiResponseData<SurfaceEquipmentDto> getDataById(Long id);
    void deleteByGeneralInformationId(Long id);
}
