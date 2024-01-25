package com.project.AICryptoInvestmentAdvice.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Date;

public interface NewsService {
    String getNews(String cryptoName, Date currentDate) throws JsonProcessingException;
}
