package com.project.AICryptoInvestmentAdvice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.AICryptoInvestmentAdvice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;



@Service
public class NewsServiceImpl implements NewsService {
    @Value("${top.news.api.url}")
    private String topNewsApiUrl;

    @Value("${all.news.api.url}")
    private String allNewsApiUrl;

    @Autowired
    @Qualifier("newsAPIRestTemplate")
    RestTemplate newsAPIRestTemplate;


    @Override
    public String getNews(String cryptoName, Date currentDate) throws JsonProcessingException {
        String res = "";
        LocalDate threeDaysBeforeCurrentDate = LocalDate.parse(currentDate.toString()).minusDays(3);
        String params = String.format("search=%s+crypto&published_after=%s", cryptoName,threeDaysBeforeCurrentDate);
        ResponseEntity<String> response = newsAPIRestTemplate.getForEntity(topNewsApiUrl+params, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode newsArrNode = objectMapper.readTree(response.getBody()).get("data");
        if(newsArrNode.size() == 0){
            params = String.format("search=%s+crypto", cryptoName);
            response = newsAPIRestTemplate.getForEntity(allNewsApiUrl+params, String.class);
        }
        newsArrNode = objectMapper.readTree(response.getBody()).get("data");
        if(newsArrNode.isArray()){
            for(JsonNode obj : newsArrNode){
                res += obj.get("title")+"###"+obj.get("url")+";";
            }
        }
        return res;
    }
}
