package com.zorroSign.BackEndTest.service;

import com.zorroSign.BackEndTest.dto.DeleteLabelDto;
import com.zorroSign.BackEndTest.dto.LabelManagementDto;
import com.zorroSign.BackEndTest.dto.UpdateLabelDto;
import com.zorroSign.BackEndTest.response.Api_Response;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

public interface MainLabelService {

    ResponseEntity<Api_Response<List<Objects>>> Create_a_new_label(LabelManagementDto labelManagementDto);
    ResponseEntity<Api_Response<List<Object>>> Get_all_labels();
    ResponseEntity<Api_Response<List<Object>>> Update_existing_labels(UpdateLabelDto updateLabelDto);
    ResponseEntity<Api_Response<List<Object>>> Delete_labels(DeleteLabelDto deleteLabelDto);

    
}
