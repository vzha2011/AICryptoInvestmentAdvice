package com.project.AICryptoInvestmentAdvice.service.impl;

import com.project.AICryptoInvestmentAdvice.dto.CryptoInfoDTO;
import com.project.AICryptoInvestmentAdvice.dto.UserDTO;
import com.project.AICryptoInvestmentAdvice.entity.AdviceRecord;
import com.project.AICryptoInvestmentAdvice.entity.User;
import com.project.AICryptoInvestmentAdvice.repository.AdviceRecordRepository;
import com.project.AICryptoInvestmentAdvice.repository.UserRepository;
import com.project.AICryptoInvestmentAdvice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AdviceRecordRepository adviceRecordRepository;

    @Override
    public boolean createUser(UserDTO userDTO){
        Optional<User> user = userRepository.findById(userDTO.getUserName());
        if(user.isPresent()){
            return false;
        }
        else{
            User newUser = new User();
            newUser.setUserName(userDTO.getUserName());
            newUser.setPassword(userDTO.getPassword());
            userRepository.save(newUser);
            return true;
        }
    }

    @Override
    public boolean validateUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findById(userDTO.getUserName());
        return user.isPresent()? user.get().getPassword().equals(userDTO.getPassword()) : false;
    }

    @Override
    public List<AdviceRecord> getAdviceRecords(String userName) {
        List<AdviceRecord> recordList = adviceRecordRepository.findByUserName(userName);

        return recordList;
    }

    @Override
    public String getNewsOfAdviceRecord(String userName, int id) {
        Optional<AdviceRecord> record = adviceRecordRepository.findByUserNameAndId(userName, id);
        if(record.isPresent()){
            return record.get().getNewsTitles();
        }
        return "";
    }

    @Override
    public String getAdviceOfAdviceRecord(String userName, int id) {
        Optional<AdviceRecord> record = adviceRecordRepository.findByUserNameAndId(userName, id);
        if(record.isPresent()){
            return record.get().getAiAdvice();
        }
        return "";
    }

}
