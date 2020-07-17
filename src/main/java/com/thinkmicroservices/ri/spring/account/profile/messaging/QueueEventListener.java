package com.thinkmicroservices.ri.spring.account.profile.messaging;

import com.thinkmicroservices.ri.spring.account.profile.messaging.events.AccountRegisteredEvent;
import com.thinkmicroservices.ri.spring.account.profile.model.Profile;
import com.thinkmicroservices.ri.spring.account.profile.service.ProfileCreateException;
import com.thinkmicroservices.ri.spring.account.profile.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 *
 * @author cwoodward
 */
//@EnableBinding(Sink.class)
@EnableBinding(ProfileQueueManager.class)
@Slf4j
public class QueueEventListener {

    @Autowired
    private ProfileService profileService;

    private static final String CONDITION_ACCOUNT_REGISTERED_EVENT = "headers['type']=='ACCOUNT_REGISTERED_EVENT'";

    private static final String CONDITION_UNMAPPED_EVENTS = "headers['type']!='ACCOUNT_REGISTERED_EVENT'";

    /**
     *
     * @param event
     */
    @StreamListener(target = Sink.INPUT, condition = CONDITION_ACCOUNT_REGISTERED_EVENT)
    public void processAccountRegisteredEvent(AccountRegisteredEvent event) {
        log.debug("Account registration event=>: " + event);
        Profile newProfile = new Profile();
        newProfile.setAccountId(event.getAccountId());
        newProfile.setEmail(event.getEmail());
        newProfile.setFirstName(event.getFirstName());
        newProfile.setMiddleName(event.getMiddleName());
        newProfile.setLastName(event.getLastName());
        try {
            profileService.create(newProfile);
        } catch (ProfileCreateException pcex) {
            log.error(pcex.getLocalizedMessage(), pcex);
        }
        log.debug("new profile created=>{}", newProfile);

    }

    /**
     *
     * @param event
     */
    @StreamListener(target = Sink.INPUT, condition = CONDITION_UNMAPPED_EVENTS)
    public void trapUnmappedEvent(Object event) {
        log.debug("Trapped unmapped account event=>: " + event);

    }

}
