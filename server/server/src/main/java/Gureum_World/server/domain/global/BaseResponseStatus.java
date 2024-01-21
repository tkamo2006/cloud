package Gureum_World.server.domain.global;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다.");
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}
