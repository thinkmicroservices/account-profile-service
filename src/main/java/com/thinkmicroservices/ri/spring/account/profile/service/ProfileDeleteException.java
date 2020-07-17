
package com.thinkmicroservices.ri.spring.account.profile.service;

import com.thinkmicroservices.ri.spring.account.profile.i18n.I18NResourceBundle;

 

/**
 *
 * @author cwoodward
 */
public class ProfileDeleteException extends ProfileException{
    private static final String RESOURCE_MESSAGE_PROFILE_DELETE_ERROR = "error.profile.delete";

    /**
     * 
     */
    public ProfileDeleteException() {
        super(RESOURCE_MESSAGE_PROFILE_DELETE_ERROR);
    }
 
   
 
}
