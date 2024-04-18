import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloWorldTest2 {
    @Test
    public void testPassed() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map")
                .then()
                .log()
                .all()
                .extract()
                .response();
        assertEquals(200, response.statusCode(), "Unexpected status code");

    }
    @Test
    public void testFailed() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map2")
                .then()
                .log()
                .all()
                .extract()
                .response();
        assertEquals(404, response.statusCode(), "Unexpected status code");

    }
}
