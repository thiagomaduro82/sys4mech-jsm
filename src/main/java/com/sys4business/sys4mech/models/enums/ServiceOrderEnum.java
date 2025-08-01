package com.sys4business.sys4mech.models.enums;

public enum ServiceOrderEnum {
    SCHEDULE("SCHEDULE"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private final String status;

    ServiceOrderEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
