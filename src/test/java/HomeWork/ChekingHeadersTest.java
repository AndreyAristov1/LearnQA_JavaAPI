package HomeWork;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChekingHeadersTest {
    @Test
    public void testChekingHeaders(){

        Response response = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);
        System.out.println();

       String responseHeader = response.header("x-secret-homework-header");
        System.out.println(responseHeader);


        assertEquals(responseHeader,"Some secret value", "Нет хедера с таким значением");



    }
}
