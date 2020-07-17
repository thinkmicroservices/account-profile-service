package com.thinkmicroservices.ri.spring.account.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author cwoodward
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profile")
public class Profile {

    @Id
    private @NonNull
    String id;
    private String accountId;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;

    private String primaryStreetAddress;
    private String secondaryStreetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String phone;
    private String dob; 

}
