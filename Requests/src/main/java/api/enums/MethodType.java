package api.enums;

public enum MethodType {
    /**
     * Удаление
     */
    DELETE("delete"),
    /**
     * Получение
     */
    GET("get"),
    /**
     * Добавление
     */
    PUT("put");

    private final String typeString;

    /**
     * Конструктор с параметрами
     * @param typeString тип запроса
     */
    MethodType(final String typeString) {
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }
}
