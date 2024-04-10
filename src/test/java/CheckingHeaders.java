import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CheckingHeaders {

    @Test
            public void testHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "myValue1");
        headers.put("myHeader2", "myValue2");

        Response response = RestAssured
                .given()
                //.headers(headers)
                .redirects()
                .follow(false)
                .when()
                //.get("https://playground.learnqa.ru/api/show_all_headers")
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();
        //Выводим хедеры запроса записанные в переменную
        response.prettyPrint();

//Выводим хедеры ответа
      //  Headers responseHeaders = response.getHeaders();
      //  System.out.println(responseHeaders);

        //Выводим конкретный заголовок
        //Записываем в переменную значение конкретного хедера
        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }
}
