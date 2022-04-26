package com.woody.api.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusCode {
    CODE_200(200, "success")
    ,CODE_400(400, "Bad Request")
    ,CODE_500(500, "Internal Server Error")

    /* login, token */
    ,CODE_601(601, "로그인 아이디를 입력해주세요.")
    ,CODE_602(602, "닉네임을 입력해주세요.")
    ,CODE_603(603, "비밀번호를 입력해주세요.")
    ,CODE_604(604, "유저 정보가 존재하지 않습니다.")
    ,CODE_605(605, "로그인 정보가 일치하지 않습니다.")
    ,CODE_651(651, "토큰이 존재하지 않습니다.")
    ,CODE_652(652, "토큰 변환 중 오류가 발생했습니다.")
    ,CODE_653(653, "토큰이 만료되었습니다.")
    ,CODE_654(654, "Refresh 토큰이 아닙니다.")
    ;

    private final Integer code;
    private final String msg;

    StatusCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}