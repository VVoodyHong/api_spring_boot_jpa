package com.woody.api.app.auth.entity;

import com.woody.api.app.user.entity.User;
import com.woody.api.common.model.type.UserType;
import lombok.Data;

import java.io.Serializable;


@Data
public class Auth implements Serializable {
    private Long idx;
    private UserType role = UserType.ROLE_USER;
    private User user;
}
