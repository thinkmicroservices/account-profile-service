package com.thinkmicroservices.ri.spring.account.profile.service;

import com.thinkmicroservices.ri.spring.account.profile.i18n.I18NResourceBundle;

 

/**
 *
 * @author cwoodward
 */
public class ProfileCreateException extends ProfileException {

    private static final String RESOURCE_MESSAGE_PROFILE_CREATE_ERROR = "error.profile.create";

    /**
     * 
     */
    public ProfileCreateException() {
        super(RESOURCE_MESSAGE_PROFILE_CREATE_ERROR);
    }

   
 
}
