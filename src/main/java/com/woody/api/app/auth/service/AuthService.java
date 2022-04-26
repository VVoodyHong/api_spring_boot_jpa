package com.woody.api.app.auth.service;

import com.woody.api.app.auth.dto.AuthDTO;
import com.woody.api.app.user.entity.User;
import com.woody.api.app.user.repository.UserRepository;
import com.woody.api.common.model.CustomException;
import com.woody.api.common.model.StatusCode;
import com.woody.api.common.util.CheckUtil;
import com.woody.api.config.security.JwtAuthenticationResponse;
import com.woody.api.config.security.JwtProvider;
import com.woody.api.config.security.JwtValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public JwtAuthenticationResponse signIn(AuthDTO.Create signInInfo) throws CustomException {
        if(CheckUtil.isEmptyString(signInInfo.getLoginId())) {
            throw new CustomException(StatusCode.CODE_601);
        } else if(CheckUtil.isEmptyString(signInInfo.getPassword())) {
            throw new CustomException(StatusCode.CODE_603);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByLoginId(signInInfo.getLoginId());
        if(user == null) {
            throw new CustomException(StatusCode.CODE_605);
        } else {
            String encode = passwordEncoder.encode(signInInfo.getPassword());
            boolean isMatch = passwordEncoder.matches(signInInfo.getPassword(), encode);
            if(isMatch) {
                Long userIdx = user.getIdx();
                String accessToken = jwtProvider.createAccessToken(String.valueOf(userIdx));
                String refreshToken = jwtProvider.createRefreshToken(String.valueOf(userIdx));
                return new JwtAuthenticationResponse(accessToken, refreshToken);
            } else {
                throw new CustomException(StatusCode.CODE_605);
            }
        }
    }

    @Transactional(readOnly = true)
    public JwtAuthenticationResponse getNewAccessToken(HttpServletRequest request) throws CustomException {
        String jwt = getJwtFromRequest(request);
        JwtValidation jwtValidation = jwtProvider.validateToken(jwt);
        if(jwt == null) {
            throw new CustomException(StatusCode.CODE_400);
        } else if(jwtValidation.isSuccess()) {
            if(jwtValidation.isRefreshToken()) {
                Long userIdx = jwtProvider.getUserIdxFromJwt(jwt);
                String newAccessToken = jwtProvider.createAccessToken(String.valueOf(userIdx));
                return new JwtAuthenticationResponse(newAccessToken);
            } else {
                throw new CustomException(StatusCode.CODE_654);
            }
        } else {
            throw new CustomException(StatusCode.CODE_652);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("authorization");
        if(!CheckUtil.isEmptyString(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }
}
