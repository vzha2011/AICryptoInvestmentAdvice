package com.project.AICryptoInvestmentAdvice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ResponseDTO {
    private List<ChoiceDTO> choices;
    @Getter
    @Setter
    public static class ChoiceDTO{
        private int index;
        private MessageDTO message;
    }


}

