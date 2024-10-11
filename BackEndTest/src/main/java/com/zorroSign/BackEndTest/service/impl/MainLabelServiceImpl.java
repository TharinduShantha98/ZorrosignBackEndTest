package com.zorroSign.BackEndTest.service.impl;


import com.zorroSign.BackEndTest.dto.*;
import com.zorroSign.BackEndTest.enmus.Status;
import com.zorroSign.BackEndTest.entity.MainLabelEntity;
import com.zorroSign.BackEndTest.entity.SubLabelManagementEntity;
import com.zorroSign.BackEndTest.repository.MainLabelManagementRepo;
import com.zorroSign.BackEndTest.repository.SubLabelManagementRepo;
import com.zorroSign.BackEndTest.response.Api_Response;
import com.zorroSign.BackEndTest.service.MainLabelService;
import com.zorroSign.BackEndTest.utils.ResponseCodes;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class MainLabelServiceImpl implements MainLabelService {


    private final MainLabelManagementRepo mainLabelManagementRepo;
    private final ModelMapper modelMapper;
    private final SubLabelManagementRepo subLabelManagementRepo;


    @Autowired
    public MainLabelServiceImpl(MainLabelManagementRepo mainLabelManagementRepo, ModelMapper modelMapper, SubLabelManagementRepo subLabelManagementRepo) {
        this.mainLabelManagementRepo = mainLabelManagementRepo;
        this.modelMapper = modelMapper;
        this.subLabelManagementRepo = subLabelManagementRepo;
    }


    @Override
    public ResponseEntity<Api_Response<List<Objects>>> Create_a_new_label(LabelManagementDto labelManagementDto) {

        MainLabelEntity mainLabelEntity =  new MainLabelEntity();
        mainLabelEntity.setLabelName(labelManagementDto.getLabel());
        mainLabelEntity.setCreateDate(new Date());
        mainLabelEntity.setStatus(Status.ACTIVE.name());
        mainLabelEntity.setUpdateDate(new Date());

        try{
            MainLabelEntity save = mainLabelManagementRepo.save(mainLabelEntity);
            List<SubLabelManagementDto> subLabelManagementDtoList = labelManagementDto.getSubLabelManagementDtoList();
            if(!subLabelManagementDtoList.isEmpty()){
                subLabelManagementDtoList.stream().map(subLabelManagementDto -> {
                    SubLabelManagementEntity subLabelManagementEntity = new SubLabelManagementEntity();
                    subLabelManagementEntity.setSubLabelName(subLabelManagementDto.getSubLabel());
                    subLabelManagementEntity.setCreateDate(new Date());
                    subLabelManagementEntity.setMainLabel(save);

                    subLabelManagementRepo.save(subLabelManagementEntity);
                    return subLabelManagementDto;
                }).toList();
            }
            return new ResponseEntity<>(new Api_Response<>(ResponseCodes.SUCCESS, "label save success ", null), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(new Api_Response<>(ResponseCodes.FAILED, "failed", null), HttpStatus.OK);

        }
    }

    @Override
    public ResponseEntity<Api_Response<List<Object>>> Get_all_labels() {


        List<LabelManagementDto> labelManagementDtos = new ArrayList<>();


        Optional<List<MainLabelEntity>> byAllStatus = mainLabelManagementRepo.findAllByStatus(Status.ACTIVE.name());
        if(byAllStatus.isEmpty()){
            return new ResponseEntity<>(new Api_Response<>(ResponseCodes.FAILED, "failed", null), HttpStatus.OK);
        }
        List<MainLabelEntity> mainLabelEntities = byAllStatus.get();


        List<Object> labelManagementDtoList =  mainLabelEntities.stream().map(mainLabelEntity -> {
            LabelManagementDto labelManagementDto=  new LabelManagementDto();
            labelManagementDto.setLabel(mainLabelEntity.getLabelName());

            Optional<List<SubLabelManagementEntity>> allBySubMainLabel = subLabelManagementRepo.findAllByMainLabel(mainLabelEntity);
            if(allBySubMainLabel.isEmpty()){
                return new ResponseEntity<>(new Api_Response<>(ResponseCodes.FAILED, "failed", null), HttpStatus.OK);
            }

            List<SubLabelManagementEntity> subLabelManagementEntities = allBySubMainLabel.get();

            List<SubLabelManagementDto> list = subLabelManagementEntities.stream().map(subLabelManagementEntity -> {
                SubLabelManagementDto subLabelManagementDto = new SubLabelManagementDto();
                subLabelManagementDto.setSubLabel(subLabelManagementEntity.getSubLabelName());
                return subLabelManagementDto;
            }).toList();



            labelManagementDto.setSubLabelManagementDtoList(list);
            return labelManagementDto;

        }).toList();
        return new ResponseEntity<>(new Api_Response<>(ResponseCodes.SUCCESS, "label save success ", labelManagementDtoList), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Api_Response<List<Object>>> Update_existing_labels(UpdateLabelDto updateLabelDto) {


        Optional<MainLabelEntity>byAllStatus = mainLabelManagementRepo.findByIdAndStatus(updateLabelDto.getUpdateLabelId(),Status.ACTIVE.name());
        if(byAllStatus.isEmpty()){
            return new ResponseEntity<>(new Api_Response<>(ResponseCodes.FAILED, "failed", null), HttpStatus.OK);
        }
        MainLabelEntity mainLabelEntitie = byAllStatus.get();
        if(!updateLabelDto.getUpdateLabelName().isEmpty()){
            mainLabelEntitie.setLabelName(updateLabelDto.getUpdateLabelName());
        }
        List<UpdateSubLabelDto> updateSubLabelDtoList = updateLabelDto.getUpdateSubLabelDtoList();

        for(UpdateSubLabelDto updateSubLabelDto: updateSubLabelDtoList){
            Optional<SubLabelManagementEntity> byId = subLabelManagementRepo.findById(updateSubLabelDto.getUpdateSubLabelId());
            if(byId.isPresent()){
                SubLabelManagementEntity subLabelManagementEntity = byId.get();
                if(!updateSubLabelDto.getUpdateSubLabelName().isEmpty()){
                    subLabelManagementEntity.setSubLabelName(updateSubLabelDto.getUpdateSubLabelName());
                }
            }

        }

        return new ResponseEntity<>(new Api_Response<>(ResponseCodes.SUCCESS, "Update success ", null), HttpStatus.OK);

    }



    @Override
    public ResponseEntity<Api_Response<List<Object>>> Delete_labels(DeleteLabelDto deleteLabelDto) {

        Optional<MainLabelEntity> byId = mainLabelManagementRepo.findById(deleteLabelDto.getLabelId());

        if(byId.isPresent()){
            MainLabelEntity mainLabelEntity = byId.get();
            mainLabelEntity.setStatus(Status.DELETE.name());
            mainLabelManagementRepo.save(mainLabelEntity);
            return new ResponseEntity<>(new Api_Response<>(ResponseCodes.SUCCESS, "Update success  ", null), HttpStatus.OK);
        }

         return new ResponseEntity<>(new Api_Response<>(ResponseCodes.FAILED, "Update failed  ", null), HttpStatus.OK);
    }
}
