package com.ceraphi.services;

import com.ceraphi.dto.GeneralInformationDto;
import com.ceraphi.dto.InputsDto;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.PageableResponse;

import java.util.List;

public interface GeneralInfoServices {
    public ApiResponseData<GeneralInformationDto> saveGeneralInformation(GeneralInformationDto generalInfo);
    public ApiResponseData<GeneralInformationDto> updateGeneralInformation(Long id, GeneralInformationDto updatedGeneralInformation);
//    public PageableResponse getAllTheProjects(int pageNo, int pageSize, String sortBy, String sortDir);
     GeneralInformationDto getGeneralInformationById(Long id);
    List<GeneralInformationDto>getByUserId(Long id);
    List<GeneralInformationDto>getProjects();
      void  deleteById(Long id);
       void saveInputs(InputsDto inputsDto);
    InputsDto findLastSavedData();
}
