package api.exception;

/**
 * Клас исключения при работе RequestUtils
 */
public class RequestTypeException extends Exception {

    private final TypeErrorCode typeErrorCode;

    /**
     * Конструктор с параметром
     * @param typeErrorCode - код ошибки с сообщением
     */
    public RequestTypeException(final TypeErrorCode typeErrorCode) {
        this.typeErrorCode = typeErrorCode;
    }

    /**
     * Метод возращает код ошибки исключения
     * @return typeErrorCode
     */
    public TypeErrorCode requestTypeException() {
        return typeErrorCode;
    }
}

