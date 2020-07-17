package com.thinkmicroservices.ri.spring.account.profile.exception.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkmicroservices.ri.spring.account.profile.i18n.I18NResourceBundle;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

/**
 *
 * @author cwoodward
 */
@Data
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String localizedMessage;
  

    /**
     *
     */
    public ApiError() {
        timestamp = LocalDateTime.now();
    }

    /**
     *
     * @param status
     */
    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    /**
     *
     * @param ex
     */
    public ApiError(Throwable ex) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     *
     * @param status
     * @param ex
     */
    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = ex.getMessage();
        this.localizedMessage = I18NResourceBundle.translateForLocale(ex.getMessage());
    }

 

}
