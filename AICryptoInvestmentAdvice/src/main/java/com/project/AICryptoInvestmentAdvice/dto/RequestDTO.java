package com.project.AICryptoInvestmentAdvice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDTO {
    private String model;
    private List<MessageDTO> messages;
    private int n;
    private double temperature;
//    private int maxTokens;

    public RequestDTO() {
    }

    public RequestDTO(String model, List<MessageDTO> messages, int n, double temperature) {
        this.model = model;
        this.messages = messages;
        this.n = n;
        this.temperature = temperature;
    }

    public String toString(){
        return this.model + this.messages + this.n + this.temperature ;
    }
}


