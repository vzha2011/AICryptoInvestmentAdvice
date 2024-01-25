package com.project.AICryptoInvestmentAdvice.repository;

import com.project.AICryptoInvestmentAdvice.entity.AdviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdviceRecordRepository extends JpaRepository<AdviceRecord, Integer> {

    List<AdviceRecord> findByUserName(String userName);

    Optional<AdviceRecord> findByUserNameAndId(String userName, int id);
}

