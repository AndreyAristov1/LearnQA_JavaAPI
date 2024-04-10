import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GetCookie {

    @Test
    public void testGetCookie(){
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");


        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();
//Выводим текст ответа
        System.out.println("\nPretty text: ");
        response.prettyPrint();

        //Выводим хедеры ответа
        System.out.println("\nHeaders: ");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        //Выводим куки
        System.out.println("\nCookies: ");
       // Map<String, String> responseCookies = response.getCookies();
        String responseCookies = response.getCookie("auth_cookie");
        System.out.println(responseCookies);

    }
}
