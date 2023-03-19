package api.utils;

import api.exception.RequestTypeException;
import api.exception.TypeErrorCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

/**
 * RequestUtils - класс с методом для составления и отправки запросов
 */
public class RequestUtils {

    /**
     * Конструктор, устанавливающий базовую url строку
     * @param url url строка
     */
    public RequestUtils(final String url) {
        RestAssured.baseURI = url;
    }

    /**
     * Метод по составлению и отправки запросов http типов
     * @param requestBody тело запроса
     * @param type тип запроса
     * @return объект типа Response
     */
    public Response createAndSendRequest(final JSONObject requestBody, final String type) throws RequestTypeException {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());

        return switch (type.trim()) {
            case ("get") -> request.get(requestBody.getString("id"));
            case ("put") -> request.put();
            //case ("post") -> request.put();
            case ("delete") -> request.delete(requestBody.getString("id"));
            default -> throw new RequestTypeException(TypeErrorCode.WRONG_TYPE_ERROR);
        };
    }
}
