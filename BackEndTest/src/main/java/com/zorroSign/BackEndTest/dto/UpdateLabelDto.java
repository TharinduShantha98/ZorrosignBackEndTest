package com.zorroSign.BackEndTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateLabelDto {


    private long updateLabelId;
    private String updateLabelName;
    private List<UpdateSubLabelDto> updateSubLabelDtoList;



}
