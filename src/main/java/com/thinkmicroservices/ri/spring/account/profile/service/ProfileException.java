
package com.thinkmicroservices.ri.spring.account.profile.service;

import com.thinkmicroservices.ri.spring.account.profile.i18n.I18NResourceBundle;
 

/**
 *
 * @author cwoodward
 */
 public abstract class ProfileException extends RuntimeException {
    
     /**
      * 
      * @param message 
      */
    public ProfileException(String message){
        super(message);
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getLocalizedMessage() {
        return I18NResourceBundle.translateForLocale(this.getMessage());
    }
    
}
