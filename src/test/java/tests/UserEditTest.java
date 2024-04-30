package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserEditTest extends BaseTestCase {
    String LoginURL = "https://playground.learnqa.ru/api/user/login";
    String EditeURL = "https://playground.learnqa.ru/api/user/";


    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    String userId;

    @Test
    public void testJustCreatedTest() {

//Создаём пользователя

        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post(EditeURL)
                .jsonPath();

        String userId = responseCreateAuth.getString("id");
        this.userId = userId;

        //Логин

        Map<String, String> authData = new HashMap<>();

        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.AuthorizationRequest(LoginURL, authData);

        //Редактирование

        String newName = "Changed Name";
        Map<String, String> editeData = new HashMap<>();
        editeData.put("firstName", newName);

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editeData)
                .put(EditeURL + userId)
                .andReturn();
        //Получаем отредактированного пользователя

        Response respounseUserData = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get(EditeURL + userId)
                .andReturn();

        Assertions.asserJsonByName(respounseUserData, "firstName", newName);


    }

    @Test
    @Description("Негативные тесты")
    @DisplayName("Редактирование пользователя без авторизации")
    public void EditingUserWithoutAuthorization() {
        Response responseEditUser = RestAssured
                .given()
                .put(EditeURL + userId)
                .andReturn();
        responseEditUser.prettyPrint();

        Assertions.assertResponseCodeEquals(responseEditUser, 404);
    }

    @Test
    @DisplayName("Редактирование другого пользователя")
    public void EditingAnotherUser() {

        Map<String, String> authData = new HashMap<>();

        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.AuthorizationRequest(LoginURL, authData);


        String newName = "New Name";
        Map<String, String> editeData = new HashMap<>();
        editeData.put("firstName", newName);

        Response responseEditAnotherUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editeData)
                .put(EditeURL + userId)
                .andReturn();
        Assertions.assertResponseCodeEquals(responseEditAnotherUser, 404);
    }

    @Test
    @DisplayName("Редактирование email пользователя на новый email без символа @ ")

    public void testEmailEditing() {


        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post(EditeURL)
                .jsonPath();

        String userId = responseCreateAuth.getString("id");
        this.userId = userId;


        Map<String, String> authData = new HashMap<>();

        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.AuthorizationRequest(LoginURL, authData);


        String email = "vinkotovexample.com";
        Map<String, String> editeData = new HashMap<>();
        editeData.put("email", email);

        Response responseEditEmail = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editeData)
                .put(EditeURL + userId)
                .andReturn();

        Assertions.assertResponseCodeEquals(responseEditEmail, 400);
        Assertions.assertResponseTextEquals(responseEditEmail, "{\"error\":\"Invalid email format\"}");
    }

    @Test
    @DisplayName("Негативные тесты на PUT ")

    public void testEditingForVeryShortName() {


        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post(EditeURL)
                .jsonPath();

        String userId = responseCreateAuth.getString("id");
        this.userId = userId;


        Map<String, String> authData = new HashMap<>();

        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.AuthorizationRequest(LoginURL, authData);


        String newName = "C";
        Map<String, String> editeData = new HashMap<>();
        editeData.put("firstName", newName);

        Response responseEditFirstName = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editeData)
                .put(EditeURL + userId)
                .andReturn();

        Assertions.assertResponseCodeEquals(responseEditFirstName, 400);
        Assertions.assertResponseTextEquals(responseEditFirstName, "{\"error\":\"The value for field `firstName` is too short\"}");
    }
}