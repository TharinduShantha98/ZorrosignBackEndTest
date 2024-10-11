package com.zorroSign.BackEndTest.repository;

import com.zorroSign.BackEndTest.entity.MainLabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainLabelManagementRepo extends JpaRepository<MainLabelEntity,Long> {

    Optional<List<MainLabelEntity>> findAllByStatus(String status);
    Optional<MainLabelEntity> findByIdAndStatus(Long id ,String status);
}
