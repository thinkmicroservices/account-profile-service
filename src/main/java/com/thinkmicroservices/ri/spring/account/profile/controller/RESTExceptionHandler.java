package com.thinkmicroservices.ri.spring.account.profile.controller;

import com.thinkmicroservices.ri.spring.account.profile.exception.api.ApiError;
import com.thinkmicroservices.ri.spring.account.profile.i18n.I18NLocaleResolver;
import com.thinkmicroservices.ri.spring.account.profile.i18n.I18NResourceBundle;
import com.thinkmicroservices.ri.spring.account.profile.service.ProfileCreateException;
import com.thinkmicroservices.ri.spring.account.profile.service.ProfileDeleteException;
import com.thinkmicroservices.ri.spring.account.profile.service.ProfileUpdateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * this class converts selected exceptions into API errors
 *
 * @author cwoodward
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RESTExceptionHandler extends ResponseEntityExceptionHandler {

     @Autowired
     I18NResourceBundle bundle;
    /**
     *
     * @param apiError
     * @return
     */
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ProfileCreateException.class)
    public ResponseEntity<Object> handleProfileCreate(Exception ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex);
       // error.setLocalizedMessage(I18NResourceBundle.translateForLocale(ex.getMessage()));
        return buildResponseEntity(error);

    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ProfileUpdateException.class)
    public ResponseEntity<Object> handleProfileUpdate(Exception ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex);

        return buildResponseEntity(error);

    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ProfileDeleteException.class)
    public ResponseEntity<Object> handleProfileDelete(Exception ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex);

        return buildResponseEntity(error);

    }
}
