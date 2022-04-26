package com.woody.api.app.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class UserDTO {

    @Data
    public static class Info {
        private Long idx;
        private String loginId;
        private String nickname;
        private String password;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }

    @Data
    public static class Create {
        private String loginId;
        private String nickname;
        private String password;
    }
}
