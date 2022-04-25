package uz.pdp.mockaroo.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {

    String message;
    private T data;

    private AppErrorDTO error;

    private boolean success;

    private Long totalCount;

    public ApiResponse(boolean success) {
        this.success = success;
    }
    public ApiResponse(String message,boolean success) {
        this.message=message;
        this.success = success;
    }

    public ApiResponse(T data) {
        this.data = data;
        this.success = true;
    }

    public ApiResponse(String message, T data) {
        this(data);
        this.message = message;
    }

    public ApiResponse(AppErrorDTO error) {
        this.error = error;
        this.success = false;
    }

    public ApiResponse(T data, Long totalCount) {
        this.data = data;
        this.success = true;
        this.totalCount = totalCount;
    }
}
