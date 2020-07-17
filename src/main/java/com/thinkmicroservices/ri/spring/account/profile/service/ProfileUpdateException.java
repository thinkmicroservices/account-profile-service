
package com.thinkmicroservices.ri.spring.account.profile.service;

import com.thinkmicroservices.ri.spring.account.profile.i18n.I18NResourceBundle;

 

/**
 *
 * @author cwoodward
 */
public class ProfileUpdateException extends ProfileException {
     private static final String RESOURCE_MESSAGE_PROFILE_UPDATE_ERROR = "error.profile.update";

    /**
     * 
     */
    public ProfileUpdateException() {
        super(RESOURCE_MESSAGE_PROFILE_UPDATE_ERROR);
    }

  

 
   
}
