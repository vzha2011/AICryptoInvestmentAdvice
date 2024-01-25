package com.project.AICryptoInvestmentAdvice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@Entity
@Table(name = "advice_record")
public class AdviceRecord {
    @Id
    private int id;
    private String name;
    private String exchange;
    @Column(name = "purchase_quantity")
    private double purchaseQuantity;
    @Column(name = "purchase_price")
    private double purchasePrice;
    @Column(name = "purchase_year")
    private int purchaseYear;
    @Column(name = "current_price")
    private double currentPrice;
    @Column(name = "current_price_date")
    private Date currentDate;
    @Column(name = "target_price")
    private double targetPrice;
    @Column(name = "target_year")
    private int targetYear;
    @Column(name = "news_titles")
    private String newsTitles;
    @Column(name = "ai_advice")
    private String aiAdvice;
    @Column(name = "user_name")
    private String userName;

    public AdviceRecord() {
    }

    @Override
    public String toString() {
        return "AdviceRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exchange='" + exchange + '\'' +
                ", purchaseQuantity=" + purchaseQuantity +
                ", purchasePrice=" + purchasePrice +
                ", currentPrice=" + currentPrice +
                ", currentDate=" + currentDate +
                ", targetPrice=" + targetPrice +
                ", targetYear=" + targetYear +
                ", newsTitles='" + newsTitles + '\'' +
                ", aiAdvice='" + aiAdvice + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
