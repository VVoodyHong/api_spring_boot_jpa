package com.woody.api.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    ROLE_USER("USER", "유저"),
    ROLE_ADMIN("ADMIN", "관리자");

    private final String role;
    private final String description;


}
