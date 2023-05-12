package api.enums;

/**
 * Класс перечисления кодов состояний ответа
 */
public enum StatusCode {
    /**
     * В случае успешной работы http метода
     */
    SUCCSESS(200),
    /**
     * В случае ошибки со стороны клиента
     */
    CLIENT_ERROR(404),
    /**
     * В случае ошибки со стороны сервера
     */
    SERVER_ERROR(500);

    private final int statusCode;

    /**
     * Конструктор с параметрами
     * @param statusCode код статуса
     */
    StatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
