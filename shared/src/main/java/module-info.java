module com.jzargo.shared.main {
    requires jakarta.validation;
    requires static lombok;
    exports com.jzargo.shared.model;
    exports com.jzargo.shared.filters;
    exports com.jzargo.shared.common;
    opens com.jzargo.shared.model;

}