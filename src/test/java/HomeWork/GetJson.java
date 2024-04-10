package HomeWork;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetJson {

    @Test
    public void testGetJson(){
        Map<String, String> params = new HashMap<>();
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
       // response.prettyPrint();

        ArrayList answer = response.get("messages");
        Object answer2 = answer.get(1);
        System.out.println(answer);
        System.out.println(answer2);
    }
}
