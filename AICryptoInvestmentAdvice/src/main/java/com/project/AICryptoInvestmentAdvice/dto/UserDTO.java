package com.project.AICryptoInvestmentAdvice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Valid
public class UserDTO {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}

