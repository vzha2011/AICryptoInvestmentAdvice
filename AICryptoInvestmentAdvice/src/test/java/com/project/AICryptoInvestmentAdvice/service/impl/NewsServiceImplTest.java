package com.project.AICryptoInvestmentAdvice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.AICryptoInvestmentAdvice.configuration.AICryptoInvestmentAdviceConfiguration;
import com.project.AICryptoInvestmentAdvice.service.NewsService;
import jakarta.inject.Inject;
import jakarta.persistence.PostRemove;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AICryptoInvestmentAdviceConfiguration.class})
@TestPropertySource(properties = "classpath:application.properties")
class NewsServiceImplTest {

   @InjectMocks
   private NewsServiceImpl newsService;

    @Mock
    @Qualifier("newsAPIRestTemplate")
    RestTemplate newsAPIRestTemplate;



    @Test
    public void getNewsTest1() throws JsonProcessingException {
        String cryptoName = "bitcoin";
        Date date = Date.valueOf(LocalDate.now().toString());
        String newsStr = "{\"meta\":{\"found\":50,\"returned\":3,\"limit\":3,\"page\":1},\"data\":[{\"uuid\":\"0acae451-8188-4625-bf33-d1579fc51930\",\"title\":\"Bitcoin ETF approval: How has the crypto market outlook changed\",\"description\":\"The recent approval of 11 spot Bitcoin exchange-traded funds (ETFs) by the US Securities and Exchange Commission (SEC) has sent ripples of excitement through th...\",\"keywords\":\"bitcoin etf, bitcoin etf approval, ;etf, bitcoin, crypto news, cryptocurrency, us sec, bitcoin exchange-traded funds\",\"snippet\":\"The recent approval of 11 spot Bitcoin exchange-traded funds (ETFs) by the US Securities and Exchange Commission (SEC) has sent ripples of excitement through th...\",\"url\":\"https:\\/\\/economictimes.indiatimes.com\\/markets\\/cryptocurrency\\/bitcoin-etf-approval-how-has-the-crypto-market-outlook-changed\\/articleshow\\/106953320.cms\",\"image_url\":\"https:\\/\\/img.etimg.com\\/thumb\\/msid-106953309,width-1200,height-630,imgsize-152918,overlay-etmarkets\\/photo.jpg\",\"language\":\"en\",\"published_at\":\"2024-01-18T08:38:08.000000Z\",\"source\":\"economictimes.indiatimes.com\",\"categories\":[\"general\",\"business\"],\"relevance_score\":33.909733,\"locale\":\"in\"},{\"uuid\":\"674bac30-b006-4bec-a55c-47c1681f80b0\",\"title\":\"Bitcoin had a nice 2023. Why smaller crypto assets could now see bigger gains.\",\"description\":\"A weekly look at the most important moves and news in crypto and what's on the horizon in digital assets.\",\"keywords\":\"article_normal, Banking\\/Credit, Financial Services, Financial Technology, Technology, Virtual Currencies\\/Cryptocurrencies, Money\\/Currency Markets, Foreign Exchange Markets, Commodity\\/Financial Market News, Cryptocurrency Markets, Content Types, Factiva Filters, C&E Exclusion Filter, Bitcoin USD, BTCUSD, VanEck Bitcoin Strategy ETF, XBTF, VanEck Bitcoin Trust, HODL, money, currency markets, foreign exchange markets, commodity, financial market news, cryptocurrency markets, content types, factiva filters, c&e exclusion filter, banking, credit, financial services, financial technology, technology, virtual currencies, cryptocurrencies\",\"snippet\":\"Welcome back to Distributed Ledger. This is Frances Yue, crypto and markets reporter at MarketWatch.\\n\\nBitcoin BTCUSD, -1.68% rallied over 150% in 2023, as the w...\",\"url\":\"https:\\/\\/www.marketwatch.com\\/story\\/bitcoin-had-a-nice-2023-why-smaller-crypto-assets-could-now-see-bigger-gains-788dcffc?mod=mw_rss_topstories\",\"image_url\":\"https:\\/\\/images.mktw.net\\/im-783732\\/social\",\"language\":\"en\",\"published_at\":\"2024-01-17T21:11:00.000000Z\",\"source\":\"marketwatch.com\",\"categories\":[\"business\",\"general\"],\"relevance_score\":32.50066,\"locale\":\"us\"},{\"uuid\":\"5da99862-d167-4324-99cd-1860323936b0\",\"title\":\"Crypto Price Today: Bitcoin, Ether Retain Profits Despite Most Altcoins Showing Losses: Details\",\"description\":\"The overall crypto market cap rose by 0.15 percent in the last 24 hours. Bitcoin and Ether – both saw gains on Tuesday, January 16. The market, at large, rema...\",\"keywords\":\"bitcoin ether price today january 16 usd india crypto buy sell   cryptocurrency, bitcoin, ether, qutm, wrapped bitcoin, zcash, tether, usd coin, ripple, binance usd, binance coin, cardano, polygon, solana, polkadot, dogecoin, shiba inu, litecoin, uniswap, tron, monero, dash, bitcoin cash, flex, cartesi, braintrust, kishu inu, circuits of value\",\"snippet\":\"Bitcoin on Tuesday, January 16 reflected a minor gain of 0.36 percent. The asset, at the time of writing, was trading at $42,625 (roughly Rs. 35.3 lakh) as per ...\",\"url\":\"https:\\/\\/www.gadgets360.com\\/cryptocurrency\\/news\\/bitcoin-ether-price-today-january-16-usd-india-crypto-buy-sell-4872048\",\"image_url\":\"https:\\/\\/i.gadgets360cdn.com\\/large\\/india-btc_eth_pixabay_Dean_Crosby_101_large_1652675059057.jpg\",\"language\":\"en\",\"published_at\":\"2024-01-16T06:50:21.000000Z\",\"source\":\"ndtv.com\",\"categories\":[\"general\"],\"relevance_score\":29.557104,\"locale\":\"in\"}]}";
        when(newsAPIRestTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(new ResponseEntity<>(newsStr, HttpStatus.OK));
        String news = newsService.getNews(cryptoName, date);
        assertTrue(news.length() > 0);
    }

    @Test
    public void getNewsTest2() throws JsonProcessingException {
        String cryptoName = "bitcoin";
        Date date = Date.valueOf(LocalDate.now().toString());
        String newsStr = "{\"meta\":{\"found\":0,\"returned\":0,\"limit\":3,\"page\":1},\"data\":[]}";
        when(newsAPIRestTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(new ResponseEntity<>(newsStr, HttpStatus.OK));
        String news = newsService.getNews(cryptoName, date);
        assertTrue(news.length() == 0);
    }

}