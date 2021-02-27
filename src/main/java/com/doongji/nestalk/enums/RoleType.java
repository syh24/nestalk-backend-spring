package com.doongji.nestalk.enums;

import lombok.Getter;

public enum RoleType {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String value;

    RoleType(String role) {
        this.value = role;
    }
    String getValue() {
        return this.value;
    }

}
