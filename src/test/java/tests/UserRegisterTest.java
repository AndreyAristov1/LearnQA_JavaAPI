package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;


public class UserRegisterTest extends BaseTestCase {
   String URL = "https://playground.learnqa.ru/api/user/";
   private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

   @Test
   public void testCreateUserWhithExistingEmail() {
      String email = "vinkotov@example.com";

      Map<String, String> userData = new HashMap<>();
      userData.put("email", email);
      userData = DataGenerator.getRegistrationData(userData);

      Response responseCreateAuth = RestAssured
              .given()
              .body(userData)
              .post(URL)
              .andReturn();

      Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
      Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");

   }

   @Test
   public void testCreateUserSuccesfully() {

      Map<String, String> userData = DataGenerator.getRegistrationData();


      Response responseCreateAuth = RestAssured
              .given()
              .body(userData)
              .post(URL)
              .andReturn();

      Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
      Assertions.assertJsonHasField(responseCreateAuth, "id");


   }

   @Test

   @Description("Регистрация пользователя с email без @")
   public void incorrectEmailTest() {

      String email = "vinkotovexample.com";

      Map<String, String> userData = new HashMap<>();
      userData.put("email", email);
      userData = DataGenerator.getRegistrationData(userData);

      Response responseGetRegistration = apiCoreRequests
              .makePostRegistration(URL, userData);

      Assertions.assertResponseCodeEquals(responseGetRegistration, 400);
      Assertions.assertResponseTextEquals(responseGetRegistration, "Invalid email format");

   }

   @Description("Регистрация пользователя без указания одного из обязательных полей")
   @ParameterizedTest
   @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
   public void testEmptyField(String condition) {


      if (condition.equals("email")) {
         String email = null;
         Map<String, String> userData = new HashMap<>();
         userData.put("email", email);
         userData = DataGenerator.getRegistrationData(userData);

         Response responseGetRegistration = apiCoreRequests
                 .makePostRegistration(URL, userData);
         Assertions.assertResponseCodeEquals(responseGetRegistration, 400);
         Assertions.assertResponseTextEquals(responseGetRegistration, "The following required params are missed: email");


      }
      if (condition.equals("password")) {
         String password = null;
         Map<String, String> userData = new HashMap<>();
         userData.put("password", password);
         userData = DataGenerator.getRegistrationData(userData);

         Response responseGetRegistration = apiCoreRequests
                 .makePostRegistration(URL, userData);

         Assertions.assertResponseCodeEquals(responseGetRegistration, 400);
         Assertions.assertResponseTextEquals(responseGetRegistration, "The following required params are missed: password");
      }
      if (condition.equals("username")) {
         String username = null;
         Map<String, String> userData = new HashMap<>();
         userData.put("username", username);
         userData = DataGenerator.getRegistrationData(userData);

         Response responseGetRegistration = apiCoreRequests
                 .makePostRegistration(URL, userData);

         Assertions.assertResponseCodeEquals(responseGetRegistration, 400);
         Assertions.assertResponseTextEquals(responseGetRegistration, "The following required params are missed: username");
      }
      if (condition.equals("firstName")) {
         String firstName = null;
         Map<String, String> userData = new HashMap<>();
         userData.put("firstName", firstName);
         userData = DataGenerator.getRegistrationData(userData);

         Response responseGetRegistration = apiCoreRequests
                 .makePostRegistration(URL, userData);

         Assertions.assertResponseCodeEquals(responseGetRegistration, 400);
         Assertions.assertResponseTextEquals(responseGetRegistration, "The following required params are missed: firstName");
      }
      if (condition.equals("lastName")) {
         String lastName = null;
         Map<String, String> userData = new HashMap<>();
         userData.put("lastName", lastName);
         userData = DataGenerator.getRegistrationData(userData);

         Response responseGetRegistration = apiCoreRequests
                 .makePostRegistration(URL, userData);

         Assertions.assertResponseCodeEquals(responseGetRegistration, 400);
         Assertions.assertResponseTextEquals(responseGetRegistration, "The following required params are missed: lastName");
      }


   }

   @Test

   @Description("Регистрация пользователя с очень коротким именем")
   public void testVeryShortName() {

      String username = "A";

      Map<String, String> userData = new HashMap<>();
      userData.put("username", username);
      userData = DataGenerator.getRegistrationData(userData);

      Response responseGetRegistration = apiCoreRequests
              .makePostRegistration(URL, userData);

      Assertions.assertResponseCodeEquals(responseGetRegistration, 400);
      Assertions.assertResponseTextEquals(responseGetRegistration, "The value of 'username' field is too short");
   }

   @Test

   @Description("Регистрация пользователя с очень длинным именем")
   public void testVeryLongName() {

      String username = "Ahhgjgjhgjhjkgjhjkjhjjjhjlkjghjgkjnbhvhkjbkjnknkjvjhkhjkmlnjbvjknknjbjbguhhgkjhhjghhjkbghvyvgvhbhbvtygyvtvjbuiuiogvgvgcfchcfxrerdrdgfhfhtvhtcthfhvhtfhhgjgkghkuhbhvhjghjhbjkhkjhnkjbhjbkjjhilkhnkjbghjkjhklhkjbkjhkughnklnkjbkhlkhkbkhbkjbkjbkjbjblkbjyftyu";

      Map<String, String> userData = new HashMap<>();
      userData.put("username", username);
      userData = DataGenerator.getRegistrationData(userData);

      Response responseGetRegistration = apiCoreRequests
              .makePostRegistration(URL, userData);

      Assertions.assertResponseCodeEquals(responseGetRegistration, 400);
      Assertions.assertResponseTextEquals(responseGetRegistration, "The value of 'username' field is too long");
   }
}
