package java_spring.job_hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error!", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be at {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_NOT_CORRECT(1003, "Password not correct", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1004, "Username must be at {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXIST(1005, "user already exists", HttpStatus.CONFLICT),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED), // 500 401
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN), // 403
    INVALID_DOB(1008, "Your age must bt at least {min}", HttpStatus.BAD_REQUEST), // 403
    ROLE_NOT_EXIST(1009, "Role not existed", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_EXIST(1010, "Permission not existed", HttpStatus.NOT_FOUND),
    UNVERIFIED_ACCOUNT(1011, "Unverified account !", HttpStatus.UNAUTHORIZED);

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
