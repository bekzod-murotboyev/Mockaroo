package uz.pdp.mockaroo.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude( JsonInclude.Include.NON_NULL )
public class AppErrorDTO {

    private Timestamp timestamp;

    private Integer status;

    private String message;

    private String path;

    private String error;

    @Builder
    public AppErrorDTO(HttpStatus status, String message, WebRequest request){
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
//        this.path = ( (ServletWebRequest) request ).getRequest().getRequestURI();
    }

    public AppErrorDTO(HttpStatus status , String message ) {
        this.timestamp = Timestamp.valueOf( LocalDateTime.now() );
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
    }
}
