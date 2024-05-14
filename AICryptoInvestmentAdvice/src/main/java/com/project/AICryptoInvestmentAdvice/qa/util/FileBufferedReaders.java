package com.project.AICryptoInvestmentAdvice.qa.util;

import com.project.AICryptoInvestmentAdvice.qa.pages.HomePage;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
@Setter
@Getter
public class FileBufferedReaders {

    public BufferedReader browsers = new BufferedReader(new InputStreamReader(FileBufferedReaders.class.getClassLoader().getResourceAsStream("browserTypes.json")));

    public BufferedReader userInfo = new BufferedReader(new InputStreamReader(FileBufferedReaders.class.getClassLoader().getResourceAsStream("userInfo.json")));

    public BufferedReader cryptoInfo = new BufferedReader(new InputStreamReader(FileBufferedReaders.class.getClassLoader().getResourceAsStream("cryptoInfo.json")));

    public BufferedReader adviceRecordYaml = new BufferedReader(new InputStreamReader(FileBufferedReaders.class.getClassLoader().getResourceAsStream("adviceRecord.yaml")));

    public BufferedReader cryptoNames = new BufferedReader(new InputStreamReader(FileBufferedReaders.class.getClassLoader().getResourceAsStream("cryptonames.json")));

    public BufferedReader qaProperties = new BufferedReader(new InputStreamReader(FileBufferedReaders.class.getClassLoader().getResourceAsStream("qa.properties")));

    public BufferedReader driverProperties = new BufferedReader(new InputStreamReader(FileBufferedReaders.class.getClassLoader().getResourceAsStream("driver.properties")));

}
