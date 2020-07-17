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
public class ProfileCreatedEvent extends AccountEvent{
 
    
   
    public ProfileCreatedEvent(String accountId,String emailAddress){
        super(accountId,emailAddress );
         
    }
    
}
