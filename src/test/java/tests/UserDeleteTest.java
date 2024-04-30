package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDeleteTest extends BaseTestCase {


    String UserLoginUrl = "https://playground.learnqa.ru/api/user/login";
    String UserDeleteUrl = "https://playground.learnqa.ru/api/user/";
    String cookie;
    String header;
    int userIdOnAuth;
    String userId;
    String userId2;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("Удаление пользователя vinkotov@example.com")
    public void DeleteUser() {

        Map<String, String> authData = new HashMap<>();

        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest(UserLoginUrl, authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");


        Map<String, String> deleteData = new HashMap<>();

        Response responseDelete = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(deleteData)
                .delete(UserDeleteUrl + userIdOnAuth)
                .andReturn();


        Assertions.assertResponseCodeEquals(responseDelete, 400);

    }

    @Test
    @Description("Удаление пользователя позитивный кейс")

    public void DeleteUserPositiv() {


        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post(UserDeleteUrl)
                .jsonPath();

        String userId = responseCreateAuth.getString("id");
        this.userId = userId;

        Map<String, String> authData = new HashMap<>();

        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.AuthorizationRequest(UserLoginUrl, authData);

        Map<String, String> deleteData = new HashMap<>();

        Response responseDelete = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(deleteData)
                .delete(UserDeleteUrl + userId)
                .andReturn();


        Assertions.assertResponseCodeEquals(responseDelete, 200);
    }

    @Test
    @DisplayName("Получение удалённого пользователя")
    public void GettingRemoteUser() {

        Map<String, String> authData = new HashMap<>();

        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.AuthorizationRequest(UserLoginUrl, authData);


        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");


        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get(UserDeleteUrl + userId)
                .andReturn();
        Assertions.assertResponseCodeEquals(responseUserData, 400);

    }

    @Test
    @Description("Удаление пользователя другим пользователем")

    public void DeleteUserNegative() {


        Map<String, String> userData = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post(UserDeleteUrl)
                .jsonPath();

        String userId = responseCreateAuth.getString("id");
        this.userId = userId;

        Map<String, String> authData = new HashMap<>();

        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.AuthorizationRequest(UserLoginUrl, authData);

        Map<String, String> deleteData = new HashMap<>();

        Map<String, String> userData2 = DataGenerator.getRegistrationData();
        JsonPath responseCreateAuth2 = RestAssured
                .given()
                .body(userData2)
                .post(UserDeleteUrl)
                .jsonPath();

        String userId2 = responseCreateAuth2.getString("id");
        this.userId2 = userId2;

        Response responseDelete = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(deleteData)
                .delete(UserDeleteUrl + userId2)
                .andReturn();
        Assertions.assertResponseCodeEquals(responseDelete, 400);
        Assertions.assertJsonHasField(responseDelete, "error");

        String answer = responseDelete.jsonPath().getString("error");
        assertEquals(answer, "This user can only delete their own account.");

    }
}