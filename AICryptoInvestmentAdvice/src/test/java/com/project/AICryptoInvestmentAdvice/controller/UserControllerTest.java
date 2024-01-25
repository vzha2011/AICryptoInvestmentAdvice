package com.project.AICryptoInvestmentAdvice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.AICryptoInvestmentAdvice.configuration.AICryptoInvestmentAdviceConfiguration;
import com.project.AICryptoInvestmentAdvice.dto.CryptoInfoDTO;
import com.project.AICryptoInvestmentAdvice.dto.UserDTO;
import com.project.AICryptoInvestmentAdvice.entity.AdviceRecord;
import com.project.AICryptoInvestmentAdvice.entity.User;
import com.project.AICryptoInvestmentAdvice.repository.AdviceRecordRepository;
import com.project.AICryptoInvestmentAdvice.repository.UserRepository;
import com.project.AICryptoInvestmentAdvice.service.AdviceService;
import com.project.AICryptoInvestmentAdvice.service.UserService;
import com.project.AICryptoInvestmentAdvice.service.impl.AdviceServiceImpl;
import com.project.AICryptoInvestmentAdvice.service.impl.NewsServiceImpl;
import com.project.AICryptoInvestmentAdvice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AICryptoInvestmentAdviceConfiguration.class})
@TestPropertySource(properties = "classpath:application.properties")
@Import(UserController.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AdviceService adviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    AdviceRecordRepository adviceRecordRepository;


    @Test
    public void logInTest() throws Exception {
        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getUserName()).thenReturn("user123456");
        when(userDTO.getPassword()).thenReturn("123456");
        User user = mock(User.class);
        when(user.getUserName()).thenReturn("user123456");
        when(user.getPassword()).thenReturn("123456");
        when(userRepository.findById("user123456")).thenReturn(Optional.of(user));
        mockMvc.perform(post("/aicryptoinvestementadvice/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void signUp() throws Exception {
        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getUserName()).thenReturn("user123456");
        when(userDTO.getPassword()).thenReturn("123456");
        User user = mock(User.class);
        when(user.getUserName()).thenReturn("user123456");
        when(user.getPassword()).thenReturn("123456");
        when(userRepository.findById("user123456")).thenReturn(Optional.of(user));
        mockMvc.perform(post("/aicryptoinvestementadvice/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAdviceRecord() throws Exception{
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
        when(adviceRecordRepository.findByUserName("user123456")).thenReturn(new ArrayList<AdviceRecord>(List.of(adviceRecord)));
        mockMvc.perform(get("/aicryptoinvestmentadvice/advice/{username}","user123456"))
                .andExpect(status().isOk());
    }

    @Test
    public void getNewsOfAdviceRecord() throws Exception{
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
        when(adviceRecordRepository.findByUserName("user123456")).thenReturn(new ArrayList<AdviceRecord>(List.of(adviceRecord)));
        mockMvc.perform(get("/aicryptoinvestmentadvice/advice/{username}/{id}/news","user123456", 0))
                .andExpect(status().isOk());
    }

    @Test
    public void getAdviceOfRecord() throws Exception{
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
        when(adviceRecordRepository.findByUserName("user123456")).thenReturn(new ArrayList<AdviceRecord>(List.of(adviceRecord)));
        mockMvc.perform(get("/aicryptoinvestmentadvice/advice/{username}/{id}/advice","user123456", 0))
                .andExpect(status().isOk());
    }

    @Test
    public void generateSuggestion() throws Exception{
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
        mockMvc.perform(post("/aicryptoinvestmentadvice/advice")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(cryptoInfoDTO)))
                        .andExpect(status().isOk());
    }
}