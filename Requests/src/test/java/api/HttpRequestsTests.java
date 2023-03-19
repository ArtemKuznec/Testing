package api;

import api.enums.MethodType;
import api.enums.StatusCode;
import api.exception.RequestTypeException;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestsTests {
    private RequestUtils requestUtils;

    /**
     * Метод запускается перед выполнением тестов классе и
     * инициализирует requestUtils
     */
    @BeforeClass
    public void initialize() {
        requestUtils = new RequestUtils("https://petstore.swagger.io/v2/pet/");
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
     * Метод поставляет корректные данные для getPetByInvalidID теста
     * @return данные для тестирования в формате двумерного массива Object
     */
    @DataProvider
    public static Object[][] validIdList() {
        return new Object[][] {
                { "10", StatusCode.SUCCSESS.getStatusCode()},
                { "11", StatusCode.SUCCSESS.getStatusCode()},
                { "12", StatusCode.SUCCSESS.getStatusCode()},
                { "13", StatusCode.SUCCSESS.getStatusCode()},
        };
    }

    /**
     * Тест метода GET, содержащего body с невалидным ID
     * @param petId id животного
     * @param expectedStatus ожидаемый статус ответа
     * @throws RequestTypeException при ошибке в работе createAndSendRequest
     */
    @Test(dataProvider = "invalidIdList")
    public void getPetByInvalidID(final String petId, final Integer expectedStatus) throws RequestTypeException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", petId);
        Assert.assertEquals(requestUtils.createAndSendRequest(requestBody, MethodType.GET.getTypeString()).statusCode(), expectedStatus);
    }

    /**
     * Тест метода GET, содержащего body с валидным ID
     * @param petId id животного
     * @param expectedStatus ожидаемый статус ответа
     * @throws RequestTypeException при ошибке в работе createAndSendRequest
     */
    @Test(dataProvider = "validIdList")
    public void getPetByValidID(final String petId, final Integer expectedStatus) throws RequestTypeException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", petId);
        Response response = requestUtils.createAndSendRequest(requestBody, MethodType.GET.getTypeString());
        assertAll(
                () -> Assert.assertEquals(response.statusCode(), expectedStatus),
                () -> Assert.assertEquals(response.jsonPath().getString("id"), petId),
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
     * @throws RequestTypeException при ошибке в работе createAndSendRequest
     */
    @Test
    public void putPetWithEmptyBodyID() throws RequestTypeException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("","");
        Response response = requestUtils.createAndSendRequest(requestBody, MethodType.PUT.getTypeString());
        assertAll(
                () -> Assert.assertEquals(response.statusCode(), StatusCode.CLIENT_ERROR.getStatusCode()),
                () -> Assert.assertEquals(response.jsonPath().getString("message"), "com.sun.jersey.api.MessageException")
        );
    }

    /**
     * Тест метода PUT, содержащего body с валидным ID
     * @throws RequestTypeException при ошибке в работе createAndSendRequest
     */
    @Test
    public void putPetWithID() throws RequestTypeException {
        final int PET_ID = 12;
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", PET_ID);
        requestBody.put("name", "Dog");
        requestBody.put("status", "available");
        Response response = requestUtils.createAndSendRequest(requestBody, MethodType.PUT.getTypeString());
        assertAll(
                () -> Assert.assertEquals(response.statusCode(), StatusCode.SUCCSESS.getStatusCode()),
                () -> Assert.assertEquals(response.jsonPath().getInt("id"), PET_ID),
                () -> Assert.assertEquals(response.jsonPath().getString("name"), "Dog"),
                () -> Assert.assertNotNull(response.jsonPath().getString("photoUrls")),
                () -> Assert.assertNotNull(response.jsonPath().getString("tags")),
                () -> Assert.assertEquals(response.jsonPath().getString("status"), "available"),
                () -> Assert.assertNull(response.jsonPath().getString("message"))
        );
    }
}
