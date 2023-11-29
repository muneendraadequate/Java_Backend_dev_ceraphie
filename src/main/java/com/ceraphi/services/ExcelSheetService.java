package com.ceraphi.services;

import com.ceraphi.entities.MasterDataTables.EstimatedCostCapexHP;
import com.ceraphi.entities.MasterDataTables.EstimatedCostOpexHP;
import com.ceraphi.repository.EstimatedCostOpexHPRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelSheetService {

    @Autowired
    private EstimatedCostOpexHPRepo estimatedCostOpexHPRepo;

    public List<EstimatedCostOpexHP> getAllEstCostOpexHP() {
        return estimatedCostOpexHPRepo.findAll();
    }
}
