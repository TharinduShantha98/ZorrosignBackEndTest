package com.zorroSign.BackEndTest.controller;


import com.zorroSign.BackEndTest.dto.DeleteLabelDto;
import com.zorroSign.BackEndTest.dto.LabelManagementDto;
import com.zorroSign.BackEndTest.dto.UpdateLabelDto;
import com.zorroSign.BackEndTest.response.Api_Response;
import com.zorroSign.BackEndTest.service.MainLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/labelManagement")
@CrossOrigin
public class LabelManagementController {


    private final MainLabelService mainLabelService;

    @Autowired
    public LabelManagementController(MainLabelService mainLabelService) {
        this.mainLabelService = mainLabelService;
    }

    @PostMapping("/createNewLabel")
    public ResponseEntity<Api_Response<List<Objects>>> Create_a_new_label(@RequestBody LabelManagementDto labelManagementDto){
        return mainLabelService.Create_a_new_label(labelManagementDto);

    }

    @GetMapping("/getAll_labels")
    public ResponseEntity<Api_Response<List<Object>>> Get_all_labels(){
        return mainLabelService.Get_all_labels();

    }

    @PostMapping("/UpdateExisting_labels")
    public ResponseEntity<Api_Response<List<Object>>> Update_existing_labels(UpdateLabelDto updateLabelDto){
        return mainLabelService.Update_existing_labels(updateLabelDto);

    }
    @PostMapping("/deleteLabel")
    public ResponseEntity<Api_Response<List<Object>>> DeleteLabels(DeleteLabelDto deleteLabelDto){
        return mainLabelService.Delete_labels(deleteLabelDto);

    }
}
