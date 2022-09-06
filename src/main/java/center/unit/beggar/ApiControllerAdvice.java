package center.unit.beggar;

import center.unit.beggar.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleException() {
        return ApiResponse.failure(
                "INTERNAL_SERVER_ERROR",
                "서버 에러가 발생했습니다. 잠시 후 다시 시도해주세요."
        );
    }
}
