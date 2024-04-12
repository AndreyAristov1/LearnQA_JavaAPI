package HomeWork;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class secretPasswordTest {

    @Test
    public void GetAuthorizationCookie(){


        String[] passwords = {"password", "123456", "123456789", "12345678", "12345", "qwerty", "abc123", "football", "1234567",
                "monkey", "111111", "1234567", "letmein", "1234", "1234567890", "dragon", "baseball", "sunshine", "iloveyou",
                "trustno1", "princess", "adobe123", "123123", "welcome", "login", "admin", "qwerty123", "solo", "1q2w3e4r", "master",
                "666666", "photoshop", "1qaz2wsx", "qwertyuiop", "ashley", "mustang", "121212", "starwars", "654321", "bailey", "access",
                "flower", "555555", "passw0rd", "shadow", "lovely",  "7777777", "michael", "!@#$%^&*", "jesus", "password1",
                "superman", "hello", "charlie", "888888", "696969", "qwertyuiop", "hottie", "freedom", "aa123456", "qazwsx", "ninja",
                "azerty", "loveme", "whatever", "donald", "batman", "zaq1zaq1", "000000", "123qwe"
        };

        for (int i = 0;  i < passwords.length; i++) {
             String password;
            password = passwords[i];

            Map<String, String> data = new HashMap<>();
            data.put("login", "super_admin");
            data.put("password", password);

            Response response = RestAssured
                    .given()
                    .body(data)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            //response.print();


            String responseCookie = response.cookie("auth_cookie");
            Map<String, String> cookies = new HashMap<>();
            if(responseCookie != null) {
                cookies.put("auth_cookie", responseCookie);
            }

            Response responseForCheck = RestAssured
                    .given()
                    .cookies(cookies)
                    .when()
                    .post("https://playground.learnqa.ru/api/check_auth_cookie")
                    .andReturn();

            if(responseForCheck.print().equals("You are authorized")){
                System.out.println("Верный пароль");
            response.print();
            break;}



        }

    }
}



