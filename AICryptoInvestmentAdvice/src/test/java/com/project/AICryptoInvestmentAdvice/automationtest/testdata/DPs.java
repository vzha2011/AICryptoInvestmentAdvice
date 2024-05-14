package com.project.AICryptoInvestmentAdvice.automationtest.testdata;

import com.project.AICryptoInvestmentAdvice.qa.util.FileBufferedReaders;
import jxl.Sheet;
import jxl.Workbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Collection;
import java.util.Map;

public class DPs {

    @DataProvider(name="browserTypes")
    public Object[] browserType() throws IOException, ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileBufferedReaders().getBrowsers());
        JSONArray browsers = (JSONArray) obj.get("browsers");
        Object[] data = new Object[browsers.size()];
        for(int i = 0; i < browsers.size(); i++){
            data[i] = browsers.get(i);
        }
        return data;
    }

    @DataProvider(name = "userData")
    public Object[][] userInfo() throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileBufferedReaders().getUserInfo());
        JSONArray jsonArray = (JSONArray) jsonObject.get("users");
        Object[][] userData = new Object[jsonArray.size()][];
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj = (JSONObject) jsonArray.get(i);
            userData[i] = new Object[]{obj.get("userName"), obj.get("password")};
        }
        return userData;
    }

    @DataProvider(name = "userNames")
    public Object[] userNames() throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileBufferedReaders().getUserInfo());
        JSONArray jsonArray = (JSONArray) jsonObject.get("usernames");
        Object[] userData = new Object[jsonArray.size()];
        for(int i = 0; i < jsonArray.size(); i++){
            userData[i] = jsonArray.get(i);
        }
        return userData;
    }

    @DataProvider(name = "userAdviceRecords")
    public Object[][] userAdviceRecord() throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileBufferedReaders().getUserInfo());
        JSONArray jsonArray1 = (JSONArray) jsonObject.get("usernames");
        JSONArray jsonArray2 = (JSONArray) jsonObject.get("adviceids");
        Object[][] userData = new Object[jsonArray1.size()][];
        for(int i = 0; i < jsonArray1.size(); i++){
            userData[i] = new Object[]{jsonArray1.get(i), jsonArray2.get(i)};
        }
        return userData;
    }

    @DataProvider(name = "cryptoInfoData")
    public Object[][] cryptoInfo() throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileBufferedReaders().getCryptoInfo());
        JSONArray jsonArray = (JSONArray) jsonObject.get("cryptoInfo");
        Object[][] cryptoInfoData = new Object[jsonArray.size()][];
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj = (JSONObject) jsonArray.get(i);
            cryptoInfoData[i] = new Object[]{obj.get("name"), obj.get("exchange"), obj.get("purchaseQuantity"), obj.get("purchasePrice"), obj.get("purchaseYear"), obj.get("targetPrice"), obj.get("targetYear") };
        }
        return cryptoInfoData;
    }

    @DataProvider(name = "cryptoInfoData2")
    public Object[][] cryptoInfo2() throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileBufferedReaders().getCryptoInfo());
        JSONArray jsonArray = (JSONArray) jsonObject.get("cryptoInfo");
        Object[][] cryptoInfoData = new Object[jsonArray.size()][];
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject obj = (JSONObject) jsonArray.get(i);
            cryptoInfoData[i] = new Object[]{obj.get("name"), obj.get("exchange"), obj.get("purchaseQuantity"), obj.get("purchasePrice"), obj.get("purchaseYear"),obj.get("currentPrice"), obj.get("currentDate"), obj.get("targetPrice"), obj.get("targetYear"), obj.get("userName") };
        }
        return cryptoInfoData;
    }

    public static JSONObject cryptoInfoToJsonObject(String[] crypto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", crypto[0]);
        jsonObject.put("exchange", crypto[1]);
        jsonObject.put("purchaseQuantity", crypto[2]);
        jsonObject.put("purchasePrice", crypto[3]);
        jsonObject.put("purchaseYear", crypto[4]);
        jsonObject.put("currentPrice", crypto[5]);
        jsonObject.put("currentDate", crypto[6]);
        jsonObject.put("targetPrice", crypto[7]);
        jsonObject.put("targetYear", crypto[8]);
        jsonObject.put("userName", crypto[9]);
        return jsonObject;
    }

    @DataProvider(name = "adviceRecord")
    public Object[][] adviceRecord() throws FileNotFoundException {
        InputStream inputStream = DPs.class.getClassLoader().getResourceAsStream("adviceRecord.yaml");
        Collection<Map<String, String>> adviceRecordCollection = new Yaml().load(inputStream);
        Object[][] adviceRecord = new Object[adviceRecordCollection.size()][11];
        int i = 0;
        for (Map<String, String> recordsMap : adviceRecordCollection) {
            adviceRecord[i++] = new Object[]{recordsMap.get("User_Name"), recordsMap.get("Record_ID"), recordsMap.get("Date"),
                    recordsMap.get("Name"), recordsMap.get("Exchange"), recordsMap.get("Quantity_Purchased"),
                    recordsMap.get("Purchase_Price"), recordsMap.get("Purchase_Year"), recordsMap.get("Price"),
                    recordsMap.get("Target_Price(USD)"), recordsMap.get("Target_Year"),};

        }
        return adviceRecord;
    }

    public String[][] readExcelData(String fileName, String sheetName){
        String[][] excelData = null;
        try{
            InputStream fileInputStream = FileBufferedReaders.class.getClassLoader().getResourceAsStream(fileName);
            Workbook workbook = Workbook.getWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            int noOfCols = sheet.getColumns();
            int noOfRows = sheet.getRows();
            excelData = new String[noOfRows-1][noOfCols];
            for(int i = 1; i < noOfRows; i++){
                for(int j = 0; j < noOfCols; j++){
                    excelData[i-1][j] = sheet.getCell(j,i).getContents();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return excelData;
    }

    @DataProvider(name = "historicaldata")
    public Object[][] historicalData(){
        String[][] data = readExcelData("HistoricalData.xlt", "Sheet1");
        Object[][] res = new Object[data.length][];
        for(int i = 0; i < res.length; i++){
            res[i] = new Object[]{(data[i][0]).toLowerCase(), "kraken", "2", data[i][3], "2023", data[i][8], "2024-04-16", data[i][9], "2024", "user123"};
        }
        return res;
    }

    @DataProvider(name = "historicaldata2")
    public Object[][] historicalData2(){
        String[][] data = readExcelData("HistoricalData.xlt", "Sheet1");
        Object[][] res = new Object[data.length][];
        for(int i = 0; i < res.length; i++){
            res[i] = new Object[]{(data[i][0]).toLowerCase(), "kraken", "2", data[i][3], "2023", data[i][9], "2024"};
        }
        return res;
    }

}
