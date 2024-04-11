package HomeWork;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class LongtimeJob {

    @Test
    public void testLongtimeJob() throws InterruptedException {
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response.prettyPrint();
        String answer = response.get("token");
        int time = response.get("seconds");

        System.out.println(answer);
        System.out.println(time);
        int milseck = time * 1000;


        Map<String, String> token = new HashMap<>();

        token.put("token", answer);
        JsonPath response2 = RestAssured
                .given()
                .queryParams(token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response2.prettyPrint();
        Object status = response2.get("status");
        System.out.println(status);
        Assert.assertEquals("Job is NOT ready",status);


            Thread.sleep(milseck);

        JsonPath response3 = RestAssured
                    .given()
                    .queryParams(token)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();
            response3.prettyPrint();
        String status2 = response3.get("status");

        System.out.println(status2);
        Assert.assertEquals("Job is ready",status2);



    }
}
