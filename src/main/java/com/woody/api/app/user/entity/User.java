package com.woody.api.app.user.entity;

import com.woody.api.app.user.dto.UserDTO;
import com.woody.api.common.model.DateAudit;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String loginId;
    private String nickname;
    private String password;

    @Builder
    public User(
            String loginId,
            String nickname,
            String password
    ) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
    }

    public UserDTO.Info toUserInfoDTO() {
        return new ModelMapper().map(this, UserDTO.Info.class);
    }

}
