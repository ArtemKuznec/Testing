package api;

import api.enums.StatusCode;
import api.service.RequestService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestsTests {
    RequestService requestService;
    final String PET_ID = "12";

    /**
     * Метод запускается перед выполнением тестов классе и
     * инициализирует requestUtils
     */
    @BeforeClass
    public void initialize() {
        requestService = new RequestService("https://petstore.swagger.io/v2/pet/");
        requestService.sendDeleteRequest(PET_ID);
    }

    /**
     * Метод поставляет некорректные данные для getPetByInvalidID теста
     * @return данные для тестирования в формате двумерного массива Object
     */
    @DataProvider
    public static Object[][] invalidIdList() {
        return new Object[][] {
                { "-2", StatusCode.CLIENT_ERROR.getStatusCode()},
                { "876", StatusCode.CLIENT_ERROR.getStatusCode()},
                { "1007675750", StatusCode.CLIENT_ERROR.getStatusCode()},
                { "-24445534", StatusCode.CLIENT_ERROR.getStatusCode()},
                { "55*&*^6adsd", StatusCode.CLIENT_ERROR.getStatusCode()},
        };
    }

    /**
     * Тест метода POST
     */
    @Test
    public void PostPetRequestTest() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", PET_ID);
        requestBody.put("name", "Doggy");
        requestBody.put("status", " ");
        Response response = requestService.sendPostRequest(requestBody);
        assertAll(
                () -> Assert.assertEquals(response.statusCode(), StatusCode.SUCCSESS.getStatusCode()),
                () -> Assert.assertEquals(response.jsonPath().getString("id"), PET_ID),
                () -> Assert.assertEquals(response.jsonPath().getString("name"), "Doggy"),
                () -> Assert.assertNotNull(response.jsonPath().getString("photoUrls")),
                () -> Assert.assertNotNull(response.jsonPath().getString("tags")),
                () -> Assert.assertEquals(response.jsonPath().getString("status"), " "),
                () -> Assert.assertNull(response.jsonPath().getString("message"))
        );
    }

    /**
     * Тест метода GET, содержащего body с невалидным ID
     * @param petId id животного
     * @param expectedStatus ожидаемый статус ответа
     */
    @Test(dataProvider = "invalidIdList")
    public void getPetByInvalidIdTest(final String petId, final Integer expectedStatus) {
        Response response = requestService.sendGetRequest(petId);
        Assert.assertEquals(response.statusCode(), expectedStatus);
        Assert.assertNotNull(response.jsonPath().getString("message"));
    }

    /**
     * Тест метода GET, содержащего body с валидным ID
     */
    @Test
    public void getPetByValidIdTest() {
        Response response = requestService.sendGetRequest(PET_ID);
        assertAll(
                () -> Assert.assertEquals(response.statusCode(), 200),
                () -> Assert.assertEquals(response.jsonPath().getString("id"), PET_ID),
                () -> Assert.assertNotNull(response.jsonPath().getString("category")),
                () -> Assert.assertNotNull(response.jsonPath().getString("name")),
                () -> Assert.assertNotNull(response.jsonPath().getString("photoUrls")),
                () -> Assert.assertNotNull(response.jsonPath().getString("tags")),
                () -> Assert.assertNotNull(response.jsonPath().getString("status")),
                () -> Assert.assertNull(response.jsonPath().getString("message"))
        );
    }

    /**
     * Тест метода PUT, содержащего body с валидным ID
     */
    @Test
    public void putPetWithEmptyBodyTest() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("","");
        Response response = requestService.sendPutRequest(requestBody);
        assertAll(
                () -> Assert.assertEquals(response.statusCode(), StatusCode.CLIENT_ERROR.getStatusCode()),
                () -> Assert.assertEquals(response.jsonPath().getString("message"), "com.sun.jersey.api.MessageException")
        );
    }

    /**
     * Тест метода PUT, содержащего body с валидным ID
     */
    @Test
    public void putPetWithBodyTest() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", PET_ID);
        requestBody.put("name", "Dog");
        requestBody.put("status", "available");
        Response response = requestService.sendPutRequest(requestBody);
        assertAll(
                () -> Assert.assertEquals(response.statusCode(), StatusCode.SUCCSESS.getStatusCode()),
                () -> Assert.assertEquals(response.jsonPath().getString("id"), PET_ID),
                () -> Assert.assertEquals(response.jsonPath().getString("name"), "Dog"),
                () -> Assert.assertEquals(response.jsonPath().getString("status"), "available"),
                () -> Assert.assertNull(response.jsonPath().getString("message"))
        );
    }


    /**
     * Тест метода DELETE с невалидным ID
     * @param petId id животного
     * @param expectedStatus ожидаемый статус ответа
     */
    @Test(dataProvider = "invalidIdList")
    public void deletePetByInvalidIdTest(final String petId, final Integer expectedStatus) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", petId);
        Response response = requestService.sendDeleteRequest(petId);
        Assert.assertEquals(response.statusCode(), expectedStatus);
    }

    @AfterClass
    public void deletePetByValidId() {
        requestService.sendDeleteRequest(PET_ID).statusCode();
    }
}
