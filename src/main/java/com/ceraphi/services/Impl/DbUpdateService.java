package com.ceraphi.services.Impl;

import com.ceraphi.dto.MasterDataTablesDto.*;
import com.ceraphi.dto.ProDataBaseModelDto;
import com.ceraphi.entities.MasterDataTables.*;
import com.ceraphi.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DbUpdateService {
    @Autowired
    private ProDataBaseRepository proDataBaseRepository;
    @Autowired
    private EstimatedCostCapexDeepRepo estimatedCostCapexDeepRepo;
    @Autowired
    private EstimatedCostCapexHPRepo estimatedCostCapexHPRepo;

    @Autowired
    private EstimatedCostOpexDeepRepo estimatedCostOpexDeepRepo;
    @Autowired
    private EstimatedCostOpexHPRepo estimatedCostOpexHPRepo;
    @Autowired
    private GelDataWellRepo gelDataWellRepo;
    @Autowired
    private HeatLoadFuelsRepo heatLoadFuelsRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void updateExistingData(ProDataBaseModelDto proDataBaseModelDto) {
        ProDataBaseModel proDataBaseModel = mapToEntityProData(proDataBaseModelDto);
        proDataBaseRepository.save(proDataBaseModel);
    }

    public List<ProDataBaseModelDto> getTheDataList() {
        List<ProDataBaseModel> all = proDataBaseRepository.findAll();
        return all.stream().map(this::mapToDtoProData).collect(Collectors.toList());

    }

    @Transactional
    public void estimated_cost_Capex_Deep(EstimatedCostCapexDeepDto estimatedCostCapexDeepDto) {
        EstimatedCostCapexDeep estimatedCostCapexDeep = mapToEntityEst_Cost_Capex_Deep(estimatedCostCapexDeepDto);
        estimatedCostCapexDeepRepo.save(estimatedCostCapexDeep);
    }

    public List<EstimatedCostCapexDeepDto> getTheDataListEst_Cost_Capex_Deep() {
        List<EstimatedCostCapexDeep> all = estimatedCostCapexDeepRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Capex_Deep).collect(Collectors.toList());
    }


    @Transactional
    public void estimated_cost_Capex_HP(EstimatedCostCapexHPDto estimatedCostCapexHPDto) {
        EstimatedCostCapexHP estimatedCostCapexHP = mapToEntityEst_Cost_Capex_HP(estimatedCostCapexHPDto);
        estimatedCostCapexHPRepo.save(estimatedCostCapexHP);
    }

    public List<EstimatedCostCapexHPDto> getTheDataListEst_Cost_Capex_HP() {
        List<EstimatedCostCapexHP> all = estimatedCostCapexHPRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Capex_HP).collect(Collectors.toList());
    }


    @Transactional
    public void estimated_cost_Opex_Deep(EstimatedCostOpexDeepDto estimatedCostOpexDeepDto) {
        EstimatedCostOpexDeep estimatedCostOpexDeep = mapToEntityEst_Cost_Opex_Deep(estimatedCostOpexDeepDto);
        estimatedCostOpexDeepRepo.save(estimatedCostOpexDeep);
    }

    public List<EstimatedCostOpexDeepDto> getTheDataListEst_Cost_Opex_Deep() {
        List<EstimatedCostOpexDeep> all = estimatedCostOpexDeepRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Opex_Deep).collect(Collectors.toList());
    }
    @Transactional
    public void estimated_cost_Opex_HP(EstimatedCostOpexHpDto estimatedCostOpexHpDto) {
        EstimatedCostOpexHP estimatedCostOpexHp = mapToEntityEst_Cost_Opex_Hp(estimatedCostOpexHpDto);
        estimatedCostOpexHPRepo.save(estimatedCostOpexHp);
    }

    public List<EstimatedCostOpexHpDto> getTheDataListEst_Cost_Opex_HP() {
        List<EstimatedCostOpexHP> all = estimatedCostOpexHPRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Opex_HP).collect(Collectors.toList());
    }

    @Transactional
    public void gelDataWell(GelDataWellDto gelDataWellDto) {
        GelDataWell gelDataWell = mapToEntityGel_Data_Well(gelDataWellDto);
        gelDataWellRepo.save(gelDataWell);
    }

    public List<GelDataWellDto> getTheDataListGelDataWell() {
        List<GelDataWell> all = gelDataWellRepo.findAll();
        return all.stream().map(this::mapToDtoGel_Data_Well).collect(Collectors.toList());
    }



    @Transactional
    public void heatLoadFuelsData(HeatLoadFuelsDto heatLoadFuelsDto) {
        HeatLoadFuels heatLoadFuels = mapToEntityHeatLoadFuel(heatLoadFuelsDto);
        heatLoadFuelsRepo.save(heatLoadFuels);
    }

    public List<HeatLoadFuelsDto> getTheHeatLoadFuelList() {
        List<HeatLoadFuels> all = heatLoadFuelsRepo.findAll();
        return all.stream().map(this::mapToDtoHeatLoadFuel).collect(Collectors.toList());
    }

















    //===================================Model Mappers================================

    public ProDataBaseModel mapToEntityProData(ProDataBaseModelDto proDataBaseModelDto) {
        ProDataBaseModel proDataBaseModel = modelMapper.map(proDataBaseModelDto, ProDataBaseModel.class);
        return proDataBaseModel;
    }

    public ProDataBaseModelDto mapToDtoProData(ProDataBaseModel proDataBaseModel) {
        ProDataBaseModelDto proDataBaseModelDto = new ProDataBaseModelDto();
        proDataBaseModelDto.setId(proDataBaseModel.getId());
        proDataBaseModelDto = modelMapper.map(proDataBaseModel, ProDataBaseModelDto.class);
        return proDataBaseModelDto;
    }

    //===============================Estimated Cost Capex Deep=======================
    public EstimatedCostCapexDeep mapToEntityEst_Cost_Capex_Deep(EstimatedCostCapexDeepDto estimatedCostCapexDeepDto) {
        EstimatedCostCapexDeep est_cost_capex_deep = modelMapper.map(estimatedCostCapexDeepDto, EstimatedCostCapexDeep.class);
        return est_cost_capex_deep;
    }

    public EstimatedCostCapexDeepDto mapToDtoEst_Cost_Capex_Deep(EstimatedCostCapexDeep est_cost_capex_deep) {
        EstimatedCostCapexDeepDto estimatedCostCapexDeepDto = new EstimatedCostCapexDeepDto();
        estimatedCostCapexDeepDto.setId(est_cost_capex_deep.getId());
        estimatedCostCapexDeepDto = modelMapper.map(est_cost_capex_deep, EstimatedCostCapexDeepDto.class);
        return estimatedCostCapexDeepDto;
    }
    //=================================Estimated Cost Capex HP ================

    public EstimatedCostCapexHP mapToEntityEst_Cost_Capex_HP(EstimatedCostCapexHPDto estimatedCostCapexHPDto) {
        EstimatedCostCapexHP est_cost_capex_HP = modelMapper.map(estimatedCostCapexHPDto, EstimatedCostCapexHP.class);
        return est_cost_capex_HP;
    }

    public EstimatedCostCapexHPDto mapToDtoEst_Cost_Capex_HP(EstimatedCostCapexHP est_cost_capex_HP) {
        EstimatedCostCapexHPDto estimatedCostCapexHPDto = new EstimatedCostCapexHPDto();
        estimatedCostCapexHPDto.setId(est_cost_capex_HP.getId());
        estimatedCostCapexHPDto = modelMapper.map(est_cost_capex_HP, EstimatedCostCapexHPDto.class);
        return estimatedCostCapexHPDto;
    }

    //=================================Estimated Cost Opex Deep ================
    public EstimatedCostOpexDeep mapToEntityEst_Cost_Opex_Deep(EstimatedCostOpexDeepDto estimatedCostOpexDeepDto) {
        EstimatedCostOpexDeep est_cost_Opex_Deep = modelMapper.map(estimatedCostOpexDeepDto, EstimatedCostOpexDeep.class);
        return est_cost_Opex_Deep;
    }

    public EstimatedCostOpexDeepDto mapToDtoEst_Cost_Opex_Deep(EstimatedCostOpexDeep est_cost_opex_Deep) {
        EstimatedCostOpexDeepDto estimatedCostOpexDeepDto = new EstimatedCostOpexDeepDto();
        estimatedCostOpexDeepDto.setId(est_cost_opex_Deep.getId());
        estimatedCostOpexDeepDto = modelMapper.map(est_cost_opex_Deep, EstimatedCostOpexDeepDto.class);
        return estimatedCostOpexDeepDto;
    }
    //=================================Estimated Cost Opex HP ================

    public EstimatedCostOpexHP mapToEntityEst_Cost_Opex_Hp(EstimatedCostOpexHpDto estimatedCostOpexHPDto) {
        EstimatedCostOpexHP est_cost_Opex_Hp = modelMapper.map(estimatedCostOpexHPDto, EstimatedCostOpexHP.class);
        return est_cost_Opex_Hp;
    }

    public EstimatedCostOpexHpDto mapToDtoEst_Cost_Opex_HP(EstimatedCostOpexHP est_cost_opex_HP) {
        EstimatedCostOpexHpDto estimatedCostOpexHpDto = new EstimatedCostOpexHpDto();
        estimatedCostOpexHpDto.setId(est_cost_opex_HP.getId());
        estimatedCostOpexHpDto = modelMapper.map(est_cost_opex_HP, EstimatedCostOpexHpDto.class);
        return estimatedCostOpexHpDto;
    }
//=======================================Gel Data Well=====================================================
    public GelDataWell mapToEntityGel_Data_Well(GelDataWellDto gelDataWellDto) {
        GelDataWell gelDataWell = modelMapper.map(gelDataWellDto, GelDataWell.class);
        return gelDataWell;
    }

    public GelDataWellDto mapToDtoGel_Data_Well(GelDataWell gelDataWell) {
        GelDataWellDto gelDataWellDto = new GelDataWellDto();
        gelDataWellDto.setId(gelDataWell.getId());
        gelDataWellDto = modelMapper.map(gelDataWell, GelDataWellDto.class);
        return gelDataWellDto;
    }
    //============================Heat Load Fuel=======================================================
    public HeatLoadFuels mapToEntityHeatLoadFuel(HeatLoadFuelsDto heatLoadFuelsDto) {
        HeatLoadFuels heatLoadFuels = modelMapper.map(heatLoadFuelsDto, HeatLoadFuels.class);
        return heatLoadFuels;
    }

    public HeatLoadFuelsDto mapToDtoHeatLoadFuel(HeatLoadFuels heatLoadFuels) {
        HeatLoadFuelsDto heatLoadFuelsDto = new HeatLoadFuelsDto();
        heatLoadFuelsDto.setId(heatLoadFuels.getId());
        heatLoadFuelsDto = modelMapper.map(heatLoadFuels, HeatLoadFuelsDto.class);
        return heatLoadFuelsDto;
    }




}
