package HomeWork;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChekingCookieTest {
    @Test
    public void testChekingCookie(){

        Response response = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        response.prettyPrint();

        Map<String, String> cookies = response.getCookies();
        System.out.println(cookies);
        String responseCookie = response.cookie("HomeWork");

        assertEquals(responseCookie,"hw_value", "Нет куки с таким значением");



    }
}
