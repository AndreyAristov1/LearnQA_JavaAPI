package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {
    public static void asserJsonByName(Response Response, String name, int expectedValue){
        Response.then().assertThat().body("$", hasKey(name));

        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON не равено ожидаемому значению");

    }

    public static void asserJsonByName(Response Response, String name, String expectedValue){
        Response.then().assertThat().body("$", hasKey(name));

        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON не равено ожидаемому значению");

    }

    public static void assertResponseTextEquals(Response Response, String expectedAnswer){
        assertEquals(expectedAnswer,
                Response.asString(),
                "Текст ответа не соответствует ожидаемому");
    }

    public static void assertResponseCodeEquals(Response Response, int expectedStatusCode){
        assertEquals(expectedStatusCode,
                Response.statusCode(),
                "Статус код ответа не соответствует ожидаемому");
    }

    public static void assertJsonHasField(Response Response, String expectedFieldName){

        Response.then().assertThat().body("$", hasKey(expectedFieldName));
    }


    public static void assertJsonHasNotField(Response Response, String expectedFieldName){

        Response.then().assertThat().body("$", not(hasKey(expectedFieldName)));
    }

    public static void assertJsonHaFields(Response Response, String[] expectedFieldNames){

        for(String expectedFieldName : expectedFieldNames){
            Assertions.assertJsonHasField(Response, expectedFieldName);
        }
    }

}
