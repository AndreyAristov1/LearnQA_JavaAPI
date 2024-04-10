package HomeWork;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class redirectTest {

    @Test
    public void testRedirect() {


            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get( "https://playground.learnqa.ru/api/long_redirect")
                    .andReturn();

            response.prettyPrint();

           String location = response.getHeader("Location");
            System.out.println("URL по которому осуществляется переход " + location);

        }
}