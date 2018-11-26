public enum Plane_enum {
    PLANES("planes"),
    ID("id"),
    PLANE("plane"),
    CHARS("chars"),
    PARAMETERS("parameters"),
    ORIGIN("origin"),
    TYPE("type"),
    AMMUNITION("ammunition"),
    RADAR("radar"),
    PRICE("price"),
    HEIGHT("height"),
    LENGTH("length"),
    WINGSPAN("wingspan"),
    MODEL("model"),
    ROCKETS("rockets"),
    SEATS("seats")
    ;
    private String value;
    private Plane_enum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}