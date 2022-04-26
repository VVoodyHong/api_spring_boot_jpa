package com.woody.api.app.user.service;

import com.woody.api.app.user.dto.UserDTO;
import com.woody.api.app.user.entity.User;
import com.woody.api.app.user.repository.UserRepository;
import com.woody.api.common.model.CustomException;
import com.woody.api.common.model.StatusCode;
import com.woody.api.common.util.CheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void createUser(UserDTO.Create userInfo) throws CustomException {
        if(CheckUtil.isEmptyString(userInfo.getLoginId())) {
            throw new CustomException(StatusCode.CODE_601);
        } else if(CheckUtil.isEmptyString(userInfo.getNickname())) {
            throw new CustomException(StatusCode.CODE_602);
        } else if(CheckUtil.isEmptyString(userInfo.getPassword())) {
            throw new CustomException(StatusCode.CODE_603);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .loginId(userInfo.getLoginId())
                .nickname(userInfo.getNickname())
                .password(passwordEncoder.encode(userInfo.getPassword()))
                .build();
        userRepository.save(user).toUserInfoDTO();
    }
}
