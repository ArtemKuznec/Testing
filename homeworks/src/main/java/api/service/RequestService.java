package api.service;

import api.exception.RequestTypeException;
import api.exception.TypeErrorCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

/**
 * RequestUtils - класс с методом для составления и отправки запросов
 */
public class RequestService {

    /**
     * Конструктор, устанавливающий базовую url строку
     * @param url url строка
     */
    public RequestService(final String url) {
        RestAssured.baseURI = url;
    }

    /**
     * Метод по составлению и отправки get запроса
     * @param id идентификатор запроса
     * @return объект типа Response
     */
    public Response sendGetRequest(final String id) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        return request.get(id);
    }

    /**
     * Метод по составлению и отправки put запроса
     * @param requestBody тело запроса
     * @return объект типа Response
     */
    public Response sendPutRequest(final JSONObject requestBody) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        return request.put();
    }

    /**
     * Метод по составлению и отправки post запроса
     * @param requestBody тело запроса
     * @return объект типа Response
     */
    public Response sendPostRequest(final JSONObject requestBody) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        return request.post();
    }

    /**
     * Метод по составлению и отправки delete запроса
     * @param id идентификатор запроса
     * @return объект типа Response
     */
    public Response sendDeleteRequest(final String id) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.basePath(id);
        return request.delete();
    }

}
