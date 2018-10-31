package com.bilev.model.enums;

public enum OptionSelectedType {
    NON("NON"), INCOMPATIBLE("INCOMPATIBLE"), REQUIRED("REQUIRED");

    OptionSelectedType(String name) {
        this.name = name;
    }
    private final String name;

    public String getName() { return name; }
}
