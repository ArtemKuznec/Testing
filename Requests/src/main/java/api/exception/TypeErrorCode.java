package api.exception;

/**
 * Класс перечисления с описанием ошибок для RequestTypeException
 */
public enum TypeErrorCode {
    /**
     * Описание ошибки
     */
    WRONG_TYPE_ERROR("wrong type of http method");

    private final String errorString;

    /**
     * Конструктор с параметром
     * @param errorString - строка с сообщением об ошибке
     */
    TypeErrorCode(final String errorString) {
        this.errorString = errorString;
    }

    /**
     * Метод возращает строку с ошибкой
     * @return errorString
     */
    public String getErrorString() {
        return errorString;
    }
}
