package com.woody.api.app.auth.dto;

import lombok.Data;

public class AuthDTO {
    @Data
    public static class Create {
        private String loginId;
        private String password;
    }
}
