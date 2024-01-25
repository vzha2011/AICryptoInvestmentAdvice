package com.project.AICryptoInvestmentAdvice.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class AICryptoInvestmentAdviceConfiguration {


    @Value("${openai.api.key}")
    private String openaiApiKey;


    @Bean(name="openaiAuth")
    @Qualifier("openaiRestTemplate")
    public RestTemplate openaiRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            //System.out.println(openaiApiKey);
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    @Bean(name="newsAPI")
    @Qualifier("newsAPIRestTemplate")
    public RestTemplate newsAPIRestTemplate(){
        return new RestTemplate();
    }


}
