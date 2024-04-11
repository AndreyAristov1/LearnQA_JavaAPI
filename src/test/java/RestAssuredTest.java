import  io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class RestAssuredTest {

    @Test
    public void testRestAssured() {
//Создаём переменную для передачи нескольких параметров
        Map<String, String> params = new HashMap<>();
        params.put("name", "John");
        //Создаём переменную в которой будет храниться полученный ответ и парсим в формат json
        JsonPath response = RestAssured
                .given() //говорит что далее будет передача параметров
                // .queryParam("name", "John") //Передаём нужные параметры
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        response.prettyPrint();
//Получаем из переменной респонc ключ ансвер и записываем его в переменную
        String answer = response.get("answer");

        if (answer == null) {
            System.out.println("Такого ключа нет");
        } else {
            System.out.println(answer);
        }
    }
}
