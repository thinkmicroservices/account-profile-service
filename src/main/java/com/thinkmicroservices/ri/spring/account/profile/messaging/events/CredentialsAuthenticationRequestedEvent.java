/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.profile.messaging.events;

import java.time.ZonedDateTime;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author cwoodward
 */

@Data
public class CredentialsAuthenticationRequestedEvent extends AccountEvent {

    
    private boolean authenticated = false;

  
    public CredentialsAuthenticationRequestedEvent(){
        super();
    }

    public CredentialsAuthenticationRequestedEvent(String accountId,String email, boolean authenticated) {
        this(accountId,email, ZonedDateTime.now(), authenticated);
        
    }

    public CredentialsAuthenticationRequestedEvent(String accountId,String email, ZonedDateTime timestamp) {
        this(accountId,email, timestamp, false);
    }

    public CredentialsAuthenticationRequestedEvent(String accountId,String email, ZonedDateTime timestamp, boolean authenticated) {
        super(accountId,email, timestamp);
    
        this.authenticated = authenticated;
    }

}
