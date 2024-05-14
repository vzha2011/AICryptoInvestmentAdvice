package com.project.AICryptoInvestmentAdvice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.AICryptoInvestmentAdvice.dto.*;
import com.project.AICryptoInvestmentAdvice.entity.AdviceRecord;
import com.project.AICryptoInvestmentAdvice.qa.util.FileBufferedReaders;
import com.project.AICryptoInvestmentAdvice.service.AdviceService;
import com.project.AICryptoInvestmentAdvice.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import java.io.IOException;
import java.util.List;
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    AdviceService adviceService;

    @Autowired
    UserService userService;

    @PostMapping(value="/aicryptoinvestementadvice/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Boolean> logIn(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.validateUser(userDTO), HttpStatus.OK);
    }

    @PostMapping(value="/aicryptoinvestementadvice/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Boolean> signUp(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.OK);
    }

    @GetMapping(value="/aicryptoinvestmentadvice/advice/{username}")
    public ResponseEntity<List<AdviceRecord>> getAdviceRecord(@PathVariable(value = "username") String userName){
        return new ResponseEntity<>(userService.getAdviceRecords(userName), HttpStatus.OK);
    }

    @GetMapping(value="/aicryptoinvestmentadvice/advice/{username}/{id}/news")
    public ResponseEntity<String> getNewsOfAdviceRecord(@PathVariable(value = "username") String userName, @PathVariable(value = "id") int id){
        return new ResponseEntity<>(userService.getNewsOfAdviceRecord(userName, id), HttpStatus.OK);
    }

    @GetMapping(value="/aicryptoinvestmentadvice/advice/{username}/{id}/advice")
    public ResponseEntity<String> getAdviceOfRecord(@PathVariable(value = "username") String userName, @PathVariable(value = "id") int id){
        return new ResponseEntity<>(userService.getAdviceOfAdviceRecord(userName, id), HttpStatus.OK);
    }

    @PostMapping(value="/aicryptoinvestmentadvice/advice", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> generateSuggestion(@RequestBody CryptoInfoDTO cryptoInfo) throws HttpStatusCodeException, JsonProcessingException {
        return new ResponseEntity<>(adviceService.generateInvestmentService(cryptoInfo), HttpStatus.OK);
    }


    @GetMapping(value="/aicryptoinvestmentadvice/cryptonames", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getCryptoNames() throws IOException, ParseException {
        JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileBufferedReaders().getCryptoNames());
        return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
    }

}
