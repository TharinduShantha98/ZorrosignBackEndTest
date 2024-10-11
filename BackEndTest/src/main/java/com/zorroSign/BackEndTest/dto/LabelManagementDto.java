package com.zorroSign.BackEndTest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LabelManagementDto {


    private String label;
    private List<SubLabelManagementDto> subLabelManagementDtoList;

}
