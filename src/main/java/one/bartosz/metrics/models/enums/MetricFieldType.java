package one.bartosz.metrics.models.enums;

public enum MetricFieldType {

    STRING("java.lang.String"), BOOLEAN("java.lang.Boolean"), DOUBLE("java.lang.Double"),
    LONG("java.lang.Long"), INTEGER("java.lang.Integer");
    private final String javaClassName;
    private MetricFieldType(String javaClassName) {
        this.javaClassName = javaClassName;
    }

    public String getJavaClassName() {
        return javaClassName;
    }
}
