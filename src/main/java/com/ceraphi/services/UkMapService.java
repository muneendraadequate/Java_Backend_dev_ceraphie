package com.ceraphi.services;

import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.utils.UkWells.UkWells;
import com.ceraphi.utils.UkWells.Wells;

import java.util.List;

public interface UkMapService {
   public  List<Wells> getUkwells(String country);
}
