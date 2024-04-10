package HomeWork;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class redirectTest {

    @Test
    public void testRedirect() {
        String location = "https://playground.learnqa.ru/api/long_redirect";
        for(int i = 0; ;i ++){

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get(location)
                .andReturn();

        response.prettyPrint();

        location = response.getHeader("Location");
        System.out.println("URL по которому осуществляется переход " + location);
        int statusCode = response.statusCode();
            System.out.println("Rjl код ответа " + statusCode);
        if(statusCode ==200)break;




        }
    }
}
