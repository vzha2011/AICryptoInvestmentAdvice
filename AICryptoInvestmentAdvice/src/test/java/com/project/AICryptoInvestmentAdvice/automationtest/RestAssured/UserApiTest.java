package com.project.AICryptoInvestmentAdvice.automationtest.RestAssured;

import com.project.AICryptoInvestmentAdvice.automationtest.testdata.DPs;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;


public class UserApiTest {

    @Test(dataProvider = "userData", dataProviderClass = DPs.class)
    public void signUpTest(String userName, String password){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", userName);
        jsonObject.put("password", password);
        given().
                header("Content-Type", "application/json").
                body(jsonObject.toJSONString()).
        when().
                post("http://localhost:8856/aicryptoinvestementadvice/signup").
        then().
                assertThat().
                statusCode(200).
                extract().equals(false);
    }

    @Test(dataProvider = "userData", dataProviderClass = DPs.class)
    public void logInTest(String userName, String password){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", userName);
        jsonObject.put("password", password);
        given().
                header("Content-Type", "application/json").and().
                body(jsonObject.toJSONString()).
        when().
                post("http://localhost:8856/aicryptoinvestementadvice/login").
        then().
                assertThat().
                statusCode(200).
                extract().equals(true);
    }

}
