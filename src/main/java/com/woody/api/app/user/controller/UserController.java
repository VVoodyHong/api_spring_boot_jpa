package com.woody.api.app.user.controller;

import com.woody.api.app.user.dto.UserDTO;
import com.woody.api.app.user.service.UserService;
import com.woody.api.common.model.ApiResponse;
import com.woody.api.common.model.CustomException;
import com.woody.api.common.util.ResponseMessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "user API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "sign up")
    @PostMapping("/api/v1/app/user")
    public ResponseEntity<ApiResponse> user(@RequestBody UserDTO.Create userInfo) {
        try {
            userService.createUser(userInfo);
            return ResponseMessageUtil.successMessage();
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}
