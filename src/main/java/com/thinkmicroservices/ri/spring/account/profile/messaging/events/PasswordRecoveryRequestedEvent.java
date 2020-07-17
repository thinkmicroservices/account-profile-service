/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.profile.messaging.events;

import java.time.ZonedDateTime;
import lombok.Data;

/**
 *
 * @author cwoodward
 */
@Data
public class PasswordRecoveryRequestedEvent extends AccountEvent {

    private boolean validUser = false;

    public PasswordRecoveryRequestedEvent(String accountId,String email) {
        this(accountId,email, false);
    }

   

    public PasswordRecoveryRequestedEvent(String accountId,String email,  boolean validUser) {
        super(accountId,email, ZonedDateTime.now());

        this.validUser = validUser;
    }

}
