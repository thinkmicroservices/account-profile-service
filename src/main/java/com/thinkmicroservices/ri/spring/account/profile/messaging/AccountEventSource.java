/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.profile.messaging;

 
import org.springframework.messaging.MessageChannel;

/**
 *
 * @author cwoodward
 */
 
public interface AccountEventSource {
    String OUTPUT ="accountEventChannel";
   
    
   // @Output(OUTPUT)
    MessageChannel accountEvents();
    
     
}
