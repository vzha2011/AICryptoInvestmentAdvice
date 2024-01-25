package com.project.AICryptoInvestmentAdvice.dto;

import com.project.AICryptoInvestmentAdvice.entity.AdviceRecord;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.sql.Date;
@Getter
@Setter
@Valid
public class CryptoInfoDTO implements Serializable {
    @NotNull
    private String name;
    @NotNull
    private String exchange;
    @NotNull
    private double purchaseQuantity;
    @NotNull
    private double purchasePrice;
    @NotNull
    private int purchaseYear;
    @NotNull
    private double currentPrice;
    @NotNull
    private Date currentDate;
    @NotNull
    private double targetPrice;
    @NotNull
    private int targetYear;
    @NotNull
    private String userName;

    @Override
    public String toString() {
        return "CryptoInfoDTO{" +
                "name='" + name + '\'' +
                ", exchange='" + exchange + '\'' +
                ", purchaseQuantity=" + purchaseQuantity +
                ", purchasePrice=" + purchasePrice +
                ", purchaseYear=" + purchaseYear +
                ", currentPrice=" + currentPrice +
                ", currentDate=" + currentDate +
                ", targetPrice=" + targetPrice +
                ", targetYear=" + targetYear +
                ", userName='" + userName + '\'' +
                '}';
    }


    public static AdviceRecord generateRecord(CryptoInfoDTO cryptoInfoDTO, String newsTitles, String aiAdvice){
        AdviceRecord record = new AdviceRecord();
        record.setName(cryptoInfoDTO.getName());
        record.setExchange(cryptoInfoDTO.getExchange().equals("")?"N/A":cryptoInfoDTO.getExchange());
        record.setPurchaseQuantity(cryptoInfoDTO.getPurchaseQuantity());
        record.setPurchasePrice(cryptoInfoDTO.getPurchasePrice());
        record.setPurchaseYear(cryptoInfoDTO.getPurchaseYear());
        record.setCurrentPrice(cryptoInfoDTO.getCurrentPrice());
        record.setCurrentDate(cryptoInfoDTO.getCurrentDate());
        record.setTargetPrice(cryptoInfoDTO.getTargetPrice());
        record.setTargetYear(cryptoInfoDTO.getTargetYear());
        record.setNewsTitles(newsTitles);
        record.setAiAdvice(aiAdvice);
        record.setUserName(cryptoInfoDTO.getUserName());
        return record;
    }


}
