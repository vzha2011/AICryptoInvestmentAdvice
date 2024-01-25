package com.project.AICryptoInvestmentAdvice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.AICryptoInvestmentAdvice.dto.CryptoInfoDTO;

public interface AdviceService {
    public String generateInvestmentService(CryptoInfoDTO cryptoInfoDTO) throws JsonProcessingException;
}
