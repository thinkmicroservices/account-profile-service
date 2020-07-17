
package com.thinkmicroservices.ri.spring.account.profile.messaging.events;

 
import lombok.Data;
 
 
/**
 *
 * @author cwoodward
 */

@Data

public class AccountRegisteredEvent extends AccountEvent {
    
 private String firstName;
    private String middleName;
    private String lastName;
    
    public AccountRegisteredEvent(){
        
    }
    public AccountRegisteredEvent(String accountId,String email) {
        super(accountId,email);
    }
    public AccountRegisteredEvent(String accountId,String email,String firstName, String middleName, String lastName) {
        super(accountId,email);
        this.firstName=firstName;
        this.middleName=middleName;
        this.lastName=lastName;
    }
}
