/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.profile.messaging.events;

import lombok.Data;

/**
 *
 * @author cwoodward
 */
@Data
public class PasswordRecoveryCompletedEvent extends AccountEvent {


    public PasswordRecoveryCompletedEvent(String accountId,String email){
        super(accountId,email);
    }
    
    
}
