package com.project.AICryptoInvestmentAdvice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.AICryptoInvestmentAdvice.dto.CryptoInfoDTO;
import com.project.AICryptoInvestmentAdvice.dto.MessageDTO;
import com.project.AICryptoInvestmentAdvice.dto.RequestDTO;
import com.project.AICryptoInvestmentAdvice.dto.ResponseDTO;
import com.project.AICryptoInvestmentAdvice.entity.AdviceRecord;
import com.project.AICryptoInvestmentAdvice.repository.AdviceRecordRepository;
import com.project.AICryptoInvestmentAdvice.service.AdviceService;
import com.project.AICryptoInvestmentAdvice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AdviceServiceImpl implements AdviceService {

    @Autowired
    NewsService newsService;

    @Autowired
    AdviceRecordRepository adviceRecordRepository;

    @Autowired
    @Qualifier("openaiRestTemplate")
    RestTemplate restTemplate;


    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;
    private List<MessageDTO> messages;
    @Value("${openai.max-completions}")
    private int n;
    @Value("${openai.temperature}")
    private double temperature;
    @Value("${openai.max_tokens}")
    private int maxTokens;


    @Override
    public String generateInvestmentService(CryptoInfoDTO cryptoInfo) throws JsonProcessingException {
        System.out.println(cryptoInfo.toString());
        String news = newsService.getNews(cryptoInfo.getName(), cryptoInfo.getCurrentDate());
        String[] newsArr = news.split(";");
        System.out.println(Arrays.toString(newsArr));
        String[] titles = new String[newsArr.length];
        int index = 0;
        for(String s : newsArr){
            titles[index++] = s.split("###")[0];
        }
        String prompt = String.format("Assume I purchased %.2f %s for %.10f USD each in %d," +
                        " and the current price for %s is %.10f USD. " +
                        "My target price is %.10f USD in %d. The recent news about %s are %s. " +
                        "Analyze using these recent news and provide the investment advice "
                ,cryptoInfo.getPurchaseQuantity(), cryptoInfo.getName(), cryptoInfo.getPurchasePrice(),cryptoInfo.getPurchaseYear(),
                cryptoInfo.getName(), cryptoInfo.getCurrentPrice(),
                cryptoInfo.getTargetPrice(), cryptoInfo.getTargetYear(), cryptoInfo.getName(), Arrays.toString(titles)
        );
        System.out.println(prompt);
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setN(n);
        requestDTO.setModel(model);
        requestDTO.setTemperature(temperature);
        requestDTO.setMessages(List.of(new MessageDTO("user", prompt)));
        System.out.println(requestDTO);
        ResponseDTO responseDTO = restTemplate.postForObject(apiUrl, requestDTO, ResponseDTO.class);
        String aiAdvice = responseDTO.getChoices().get(0).getMessage().getContent();
        news = news.replaceAll("###", ", Link: ");
        news = news.replaceAll(";", "\n");
        String response = "Recent News: \n"+ news + "\n" + aiAdvice;
        System.out.println(cryptoInfo.getUserName());
        if(cryptoInfo.getUserName().equals("")){
            response = "There is no recent news for " + cryptoInfo.getName() + "\n" + aiAdvice;
        }
        AdviceRecord record = CryptoInfoDTO.generateRecord(cryptoInfo, Arrays.toString(newsArr),aiAdvice);
        adviceRecordRepository.save(record);
        System.out.println(response);
        return response;
    }
}
