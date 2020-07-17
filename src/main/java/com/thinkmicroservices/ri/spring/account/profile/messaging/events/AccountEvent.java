
package com.thinkmicroservices.ri.spring.account.profile.messaging.events;

 
 
import java.time.ZonedDateTime;
import lombok.Data;
 

/**
 *
 * @author cwoodward
 */

@Data

public abstract class AccountEvent {
    
 
    protected String accountId;
    protected String email;
    
   
    protected ZonedDateTime timestamp;

    public AccountEvent() {
        this(null,null);
    }
    
    public AccountEvent(String accountId,String email) {
        this(accountId,email,ZonedDateTime.now());
    }

    public AccountEvent(String accountId,String email, ZonedDateTime timestamp){
        this.accountId=accountId;
        this.email=email;
        this.timestamp=timestamp;
    }
    

    
 
    
}
