package Gureum_World.server.domain.global;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    BAD_REQUEST(false, 2001, "잘못된 요청입니다."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    RTK_INCORRECT(false, 2004, "Refresh Token 값을 확인해주세요."),
    FAILED_TO_LOGIN(false,3014,"없는 회원입니다.");
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}
