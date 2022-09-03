package center.unit.beggar.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ApiResponse<T> {
    private final String code;
    private final String message;
    private final T data;

    private ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
                "SUCCESS",
                "",
                null
        );
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                "SUCCESS",
                "",
                data
        );
    }

    public static <T> ApiResponse<List<T>> success(List<T> data) {
        return new ApiResponse<>(
                "SUCCESS",
                "",
                data
        );
    }

    public static <T> ApiResponse<T> failure(String resultCode, String message) {
        return new ApiResponse<>(
                resultCode,
                message,
                null
        );
    }
}
