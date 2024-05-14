package com.project.AICryptoInvestmentAdvice.aws;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DBCredentials {
    private String engine;
    private String host;
    private String port;
    private String username;
    private String password;
    private String dbInstanceIdentifier;
}