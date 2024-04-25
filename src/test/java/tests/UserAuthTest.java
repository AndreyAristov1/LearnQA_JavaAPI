package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import  io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import lib.ApiCoreRequests;


@Epic("Кейсы авторизации")
@Feature("Авторизация")
public class UserAuthTest extends BaseTestCase {
    String UserLoginUrl = "https://playground.learnqa.ru/api/user/login";
    String UserAuthUrl = "https://playground.learnqa.ru/api/user/auth";

    String cookie;
    String header;
    int userIdOnAuth;
private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @BeforeEach

    public  void loginUser(){

        Map<String, String> authData = new HashMap<>();

        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest(UserLoginUrl, authData);

        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth,"user_id");

    }
    @Test
@Description("Успешная авторизация пользователя по почте и паролю")
    @DisplayName("Позитивный тест")
    public void testAuthUser(){

        Response responseCheckAuth = apiCoreRequests
                .makeGetRequest(UserAuthUrl, this.header, this.cookie);

        Assertions.asserJsonByName(responseCheckAuth,"user_id", this.userIdOnAuth);


    }
@Description("Этот тест проверяет статус авторизации без отправки файла cookie аутентификации или хедера.")
    @DisplayName("Негативный тест авторизации пользователя")
@ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void testNegativeAuthUser(String condition) {

    if (condition.equals("cookie")) {
        Response responseForCheck = apiCoreRequests.makeGetRequestWithCookie(UserAuthUrl, this.cookie);

        Assertions.asserJsonByName(responseForCheck, "user_id", 0);
    } else if (condition.equals("headers")) {
        Response responseForCheck = apiCoreRequests.makeGetRequestWithToken(UserAuthUrl, this.header);
        Assertions.asserJsonByName(responseForCheck, "user_id", 0);
    } else {
        throw new IllegalArgumentException("Значение условия известно:" + condition);
    }
}
}
