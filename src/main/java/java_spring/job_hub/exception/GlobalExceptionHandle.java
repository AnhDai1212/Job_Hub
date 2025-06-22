package java_spring.job_hub.exception;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.ValidationException;
import java_spring.job_hub.dto.response.ApiResponse;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

// Nơi đây cau hinh cac error dien ra duoi tang filer sau khi xuong service sinh ra error

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Tự định nghĩa
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    // liên quan đến việc truy cập bị từ chối
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }
    // xảy ra khi dữ liệu đầu vào không hợp lệ, thường là trong quá trình validation
    @ExceptionHandler(
            value =
                    MethodArgumentNotValidException
                            .class) // MethodArgumentNotValidException  sẽ bắt các kiệu ValidException
//    public ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
//        String enumkey = exception.getFieldError().getDefaultMessage();
//
//        ErrorCode errorCode = ErrorCode.INVALID_KEY;
//
//        Map<String, Object> attributes = null;
//        try {
//            errorCode = ErrorCode.valueOf(enumkey);
//
//            var constrainViolation =
//                    exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
//            attributes = constrainViolation
//                    .getConstraintDescriptor()
//                    .getAttributes(); // lay cac param da truyen vao @constrain
//
//            log.info(attributes.toString());
//
//        } catch (IllegalArgumentException e) {
//
//        }
//
//        ApiResponse apiResponse = new ApiResponse();
//
//        apiResponse.setCode(errorCode.getCode());
//        apiResponse.setMessage(
//                Objects.nonNull(attributes)
//                        ? mapAttribute(errorCode.getMessage(), attributes)
//                        : errorCode.getMessage());
//
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

//    private String mapAttribute(String message, Map<String, Object> attributes) {
//        String minValue = attributes.get(MIN_ATTRIBUTE).toString();
//        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
//    }
    public ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;

        try {
            // Nếu enumKey không khớp với bất kỳ ErrorCode nào thì giữ nguyên là INVALID_KEY
            errorCode = ErrorCode.valueOf(enumKey);

            // Lấy thông tin về annotation để lấy ra các tham số truyền vào như "min", "max", ...
            var constraintViolation = exception.getBindingResult()
                    .getAllErrors()
                    .getFirst()
                    .unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info("Constraint attributes: {}", attributes);
        } catch (IllegalArgumentException | ValidationException e) {
            log.warn("Không tìm thấy ErrorCode phù hợp hoặc unwrap thất bại: {}", e.getMessage());
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());

        // Nếu message chứa {min}, {max}, ... thì thay thế bằng giá trị tương ứng, ngược lại giữ nguyên
        String finalMessage = errorCode.getMessage();
        if (attributes != null) {
            finalMessage = mapAttribute(errorCode.getMessage(), attributes);
        }

        apiResponse.setMessage(finalMessage);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return message;
        }

        // Thay thế tất cả các placeholder dạng {key} trong message nếu có trong attributes
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            if (message.contains(placeholder)) {
                String value = entry.getValue() != null ? entry.getValue().toString() : "";
                message = message.replace(placeholder, value);
            }
        }

        return message;
    }


}
