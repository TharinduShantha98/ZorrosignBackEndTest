package com.zorroSign.BackEndTest.repository;

import com.zorroSign.BackEndTest.entity.MainLabelEntity;
import com.zorroSign.BackEndTest.entity.SubLabelManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubLabelManagementRepo  extends JpaRepository<SubLabelManagementEntity,Long> {

    Optional<List<SubLabelManagementEntity>> findAllByMainLabel(MainLabelEntity mainLabelEntity);


}
