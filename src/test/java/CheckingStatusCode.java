import  io.restassured.RestAssured;
import  io.restassured.response.Response;
import org.junit.jupiter.api.Test;
public class CheckingStatusCode {

    @Test
    public void testCheckingStatusCode(){
        /*Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_500")
                .andReturn();
       //Записываем в переменную полученный статускод ответа
        int statusCode = response.statusCode();
        System.out.println(statusCode);*/

        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();
        //Записываем в переменную полученный статускод ответа
        int statusCode = response.statusCode();
        System.out.println(statusCode);

    }
}
