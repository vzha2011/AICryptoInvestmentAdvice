package com.project.AICryptoInvestmentAdvice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private String role;
    private String content;

    public MessageDTO(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public MessageDTO() {
    }
}

