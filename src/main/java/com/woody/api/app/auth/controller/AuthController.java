package com.woody.api.app.auth.controller;

import com.woody.api.app.auth.dto.AuthDTO;
import com.woody.api.app.auth.service.AuthService;
import com.woody.api.common.model.ApiResponse;
import com.woody.api.common.model.CustomException;
import com.woody.api.common.util.ResponseMessageUtil;
import com.woody.api.config.security.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "auth", description = "auth API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "sign in")
    @PostMapping("/api/v1/app/auth")
    public ResponseEntity<ApiResponse> auth(@RequestBody AuthDTO.Create signInInfo) {
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = authService.signIn(signInInfo);
            return ResponseMessageUtil.successMessage(jwtAuthenticationResponse);
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "get new Access Token")
    @GetMapping("api/v1/app/auth/accessToken")
    public ResponseEntity<ApiResponse> accessToken(HttpServletRequest request) {
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = authService.getNewAccessToken(request);
            return ResponseMessageUtil.successMessage(jwtAuthenticationResponse);
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

}
