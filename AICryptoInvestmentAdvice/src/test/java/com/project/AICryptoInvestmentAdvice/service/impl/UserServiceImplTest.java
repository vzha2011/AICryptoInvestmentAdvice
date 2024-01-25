package com.project.AICryptoInvestmentAdvice.service.impl;

import com.project.AICryptoInvestmentAdvice.configuration.AICryptoInvestmentAdviceConfiguration;
import com.project.AICryptoInvestmentAdvice.dto.CryptoInfoDTO;
import com.project.AICryptoInvestmentAdvice.dto.UserDTO;
import com.project.AICryptoInvestmentAdvice.entity.AdviceRecord;
import com.project.AICryptoInvestmentAdvice.entity.User;
import com.project.AICryptoInvestmentAdvice.repository.AdviceRecordRepository;
import com.project.AICryptoInvestmentAdvice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AICryptoInvestmentAdviceConfiguration.class})
@TestPropertySource(properties = "classpath:application.properties")
class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    AdviceRecordRepository adviceRecordRepository;

    @Test
    public void createUserTest1() {
        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getUserName()).thenReturn("user123456");
        when(userDTO.getPassword()).thenReturn("123456");
        boolean result = userService.createUser(userDTO);
        assertTrue(result);
    }

    @Test
    public void createUserTest2() {
        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getUserName()).thenReturn("user123456");
        when(userDTO.getPassword()).thenReturn("123456");
        User user = mock(User.class);
        when(user.getUserName()).thenReturn("user123456");
        when(user.getPassword()).thenReturn("123456");
        when(userRepository.findById("user123456")).thenReturn(Optional.of(user));
        boolean result = userService.createUser(userDTO);
        assertFalse(result);
    }

    @Test
    public void validateUserTest1(){
        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getUserName()).thenReturn("user123456");
        when(userDTO.getPassword()).thenReturn("123456");
        User user = mock(User.class);
        when(user.getUserName()).thenReturn("user123456");
        when(user.getPassword()).thenReturn("123456");
        when(userRepository.findById("user123456")).thenReturn(Optional.of(user));
        boolean result = userService.validateUser(userDTO);
        assertTrue(result);
    }

    @Test
    public void validateUserTest2(){
        UserDTO userDTO = mock(UserDTO.class);
        when(userDTO.getUserName()).thenReturn("user123456");
        when(userDTO.getPassword()).thenReturn("123456");
        User user = mock(User.class);
        when(user.getUserName()).thenReturn("user123456");
        when(user.getPassword()).thenReturn("12345");
        when(userRepository.findById("user123456")).thenReturn(Optional.of(user));
        boolean result = userService.validateUser(userDTO);
        assertFalse(result);
    }


    @Test
    public void getAdviceRecordsTest() {
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
        when(cryptoInfoDTO.getUserName()).thenReturn("user123456");
        AdviceRecord adviceRecord = CryptoInfoDTO.generateRecord(cryptoInfoDTO, "news", "advice");
        when(adviceRecordRepository.findByUserName("user123456")).thenReturn(new ArrayList<>(List.of(adviceRecord)));
        assertTrue(userService.getAdviceRecords("user123456").size() > 0);
    }

    @Test
    public void getNewsAndAdviceOfAdviceRecordTest1() {
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
        when(cryptoInfoDTO.getUserName()).thenReturn("user123456");
        AdviceRecord adviceRecord = CryptoInfoDTO.generateRecord(cryptoInfoDTO, "news", "advice");
        when(adviceRecordRepository.findByUserNameAndId("user123456",0)).thenReturn(Optional.of(adviceRecord));
        assertEquals(userService.getNewsOfAdviceRecord("user123456",0), "news");
        assertEquals(userService.getAdviceOfAdviceRecord("user123456",0), "advice");
    }

    @Test
    public void getNewsAndAdviceOfAdviceRecordTest2() {
        assertEquals(userService.getNewsOfAdviceRecord("user123456",0), "");
        assertEquals(userService.getAdviceOfAdviceRecord("user123456",0), "");
    }


}