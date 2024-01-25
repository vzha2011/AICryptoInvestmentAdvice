package com.project.AICryptoInvestmentAdvice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.AICryptoInvestmentAdvice.configuration.AICryptoInvestmentAdviceConfiguration;
import com.project.AICryptoInvestmentAdvice.dto.CryptoInfoDTO;
import com.project.AICryptoInvestmentAdvice.dto.MessageDTO;
import com.project.AICryptoInvestmentAdvice.dto.RequestDTO;
import com.project.AICryptoInvestmentAdvice.dto.ResponseDTO;
import com.project.AICryptoInvestmentAdvice.entity.AdviceRecord;
import com.project.AICryptoInvestmentAdvice.repository.AdviceRecordRepository;
import com.project.AICryptoInvestmentAdvice.service.AdviceService;
import com.project.AICryptoInvestmentAdvice.service.NewsService;
import net.bytebuddy.asm.Advice;
import org.apache.coyote.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AICryptoInvestmentAdviceConfiguration.class})
@TestPropertySource(properties = "classpath:application.properties")
class AdviceServiceImplTest {

    @InjectMocks
    AdviceServiceImpl adviceServiceImpl;

    @Mock
    NewsServiceImpl newsService;


    @Mock
    @Qualifier("openaiRestTemplate")
    RestTemplate openAIRestTemplate;

    @Mock
    AdviceRecordRepository adviceRecordRepository;

    @Test
    public void generateInvestmentServiceTest() throws JsonProcessingException {
        when(newsService.getNews(anyString(), any(Date.class))).thenReturn("news");
        ResponseDTO responseDTO = new ResponseDTO();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setRole("user");
        messageDTO.setContent("advice");
        ResponseDTO.ChoiceDTO choiceDTO = new ResponseDTO.ChoiceDTO();
        choiceDTO.setIndex(0);
        choiceDTO.setMessage(messageDTO);
        List<ResponseDTO.ChoiceDTO> choiceDTOList = new ArrayList<>();
        choiceDTOList.add(choiceDTO);
        responseDTO.setChoices(choiceDTOList);
        ReflectionTestUtils.setField(adviceServiceImpl, "apiUrl", "apiUrl");
        when(openAIRestTemplate.postForObject(anyString(), any(), eq(ResponseDTO.class))).thenReturn(responseDTO);
        CryptoInfoDTO cryptoInfoDTO = mock(CryptoInfoDTO.class);
        when(cryptoInfoDTO.getName()).thenReturn("bitcoin");
        Date date = Date.valueOf(LocalDate.now().toString());
        when(cryptoInfoDTO.getCurrentDate()).thenReturn(date);
        when(cryptoInfoDTO.getCurrentPrice()).thenReturn(43000.00);
        when(cryptoInfoDTO.getPurchaseQuantity()).thenReturn(2.0);
        when(cryptoInfoDTO.getExchange()).thenReturn("exchange");
        when(cryptoInfoDTO.getPurchasePrice()).thenReturn(41000.00);
        when(cryptoInfoDTO.getPurchaseYear()).thenReturn(2023);
        when(cryptoInfoDTO.getTargetYear()).thenReturn(2024);
        when(cryptoInfoDTO.getUserName()).thenReturn("user123");
        AdviceRecord adviceRecord = CryptoInfoDTO.generateRecord(cryptoInfoDTO, "news", "advice");
        assertEquals("Recent News: \n"+ "news" + "\n" + "advice", adviceServiceImpl.generateInvestmentService(cryptoInfoDTO));
        when(cryptoInfoDTO.getUserName()).thenReturn("");
        assertEquals("There is no recent news for " + "bitcoin" + "\n" + "advice", adviceServiceImpl.generateInvestmentService(cryptoInfoDTO));
    }
}