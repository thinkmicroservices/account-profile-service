/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thinkmicroservices.ri.spring.account.profile.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 * @author cwoodward
 */
public interface ProfileQueueManager {
    @Input("input")
    SubscribableChannel  inboundEvents();
    
    @Output("output")
    MessageChannel outboundEvents();
}
