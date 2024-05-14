package com.project.AICryptoInvestmentAdvice.configuration;

import com.project.AICryptoInvestmentAdvice.aws.DBCredentials;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import javax.sql.DataSource;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;

@Configuration
@PropertySource("classpath:application.properties")
public class AICryptoInvestmentAdviceConfiguration {


    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${aws.rds.secret.name}")
    private String secretName;

    @Value("${aws.rds.region}")
    private String region;


    @Bean(name="openaiAuth")
    @Qualifier("openaiRestTemplate")
    public RestTemplate openaiRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
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



    @Bean(name="awsrdsmysql")
    public DataSource dataSource(){
        try{
            AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(region).build();
            GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
            GetSecretValueResult getSecretValueResult = null;
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
            if(getSecretValueResult.getSecretString() != null){
                String secret = getSecretValueResult.getSecretString();
                System.out.println(secret);
                DBCredentials dbCredentials = new Gson().fromJson(secret, DBCredentials.class);
                String dburl = "jdbc:" + dbCredentials.getEngine() + "://" + dbCredentials.getHost() + ":" + dbCredentials.getPort() + "/AI_Investment_Advice" ;
                return DataSourceBuilder.create()
                        .driverClassName("com.mysql.cj.jdbc.Driver")
                        .password(dbCredentials.getPassword())
                        .username(dbCredentials.getUsername())
                        .url(dburl)
                        .build();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
