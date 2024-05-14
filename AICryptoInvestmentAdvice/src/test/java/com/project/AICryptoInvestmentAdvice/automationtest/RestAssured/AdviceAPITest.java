package com.project.AICryptoInvestmentAdvice.automationtest.RestAssured;

import com.project.AICryptoInvestmentAdvice.automationtest.testdata.DPs;
import static io.restassured.RestAssured.given;

import org.awaitility.Awaitility;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class AdviceAPITest {

    @Test(dataProvider = "userNames", dataProviderClass = DPs.class)
    public void getAdviceRecordTest(String userName) throws ParseException {
        given().
                header("Content-Type", "application/json").pathParam("username", userName).
        when().
                get("http://localhost:8856/aicryptoinvestmentadvice/advice/{username}").
        then().
                assertThat().statusCode(200);
    }

    @Test(dataProvider = "userAdviceRecords", dataProviderClass = DPs.class)
    public void getNewsOfAdviceRecordTest(String userName,String id) throws ParseException {
        given().
                header("Content-Type", "application/json").pathParam("username", userName).pathParam("id",id).
        when().
                get("http://localhost:8856/aicryptoinvestmentadvice/advice/{username}/{id}/news").
        then().
                assertThat().statusCode(200);
    }

    @Test(dataProvider = "userAdviceRecords", dataProviderClass = DPs.class)
    public void getAdviceOfRecordTest(String userName,String id) throws ParseException {
        given().
                header("Content-Type", "application/json").pathParam("username", userName).pathParam("id",id).
        when().
                get("http://localhost:8856/aicryptoinvestmentadvice/advice/{username}/{id}/advice").
        then().
                assertThat().statusCode(200);
    }

    @Test(dataProvider = "cryptoInfoData2", dataProviderClass = DPs.class)
    public void generateSuggestionTest(String[] crypto) throws ParseException {
        JSONObject jsonObject = DPs.cryptoInfoToJsonObject(crypto);
        Awaitility.await().atMost(5, TimeUnit.MINUTES).until(() -> getStatusOfAdviceGenerated(jsonObject)== 200);
    }

    public int getStatusOfAdviceGenerated(JSONObject jsonObject){
        return
        given().
                header("Content-Type", "application/json").
                body(jsonObject.toJSONString()).
        when().
                post("http://localhost:8856/aicryptoinvestmentadvice/advice").
        then().extract().
                statusCode();
    }


}
