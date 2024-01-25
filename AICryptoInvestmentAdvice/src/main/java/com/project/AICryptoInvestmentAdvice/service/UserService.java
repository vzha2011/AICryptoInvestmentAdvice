package com.project.AICryptoInvestmentAdvice.service;

import com.project.AICryptoInvestmentAdvice.dto.UserDTO;
import com.project.AICryptoInvestmentAdvice.entity.AdviceRecord;

import java.util.List;

public interface UserService {


    public boolean validateUser(UserDTO userDTO);

    public boolean createUser(UserDTO userDTO);

    public List<AdviceRecord> getAdviceRecords(String userName);
    public String getNewsOfAdviceRecord(String userName, int id);
    public String getAdviceOfAdviceRecord(String userName, int id);


}
