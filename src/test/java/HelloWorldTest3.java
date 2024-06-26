import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest3 {
    @ParameterizedTest
    @ValueSource(strings = {"Pety", "John", ""})
    public void testHelloMethodWithoutName(String name) {
        Map<String, String> params = new HashMap<>();

        if (name.length() > 0){
            params.put("name", name);
        }

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        String answer = response.getString("answer");
        String expextedName = (name.length() > 0) ? name : "someone";
        assertEquals("Hello, " + expextedName, answer, "The answer is not expected");

    }


}
