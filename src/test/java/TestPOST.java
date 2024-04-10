import  io.restassured.RestAssured;
import  io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestPOST {

    @Test
    public void testCheckingRequestType(){
        //Создаём переменную в которой будет передано тело запроса в json
        Map<String, Object> body = new HashMap<>();
        body.put("param1", "value1");
        body.put("params2", "value2");
        Response response = RestAssured
                .given()
                //.body("param1=value1&param2=value2")//Передача параметров в теле строкой
                //.body("{\"param1\":\"value1\",\"param2\":\"value2\"}")//Передача параметров в теле в json
                .body(body) //Передаём в тело параметры из переменной
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();
        response.print();
    }
}
