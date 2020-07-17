package com.thinkmicroservices.ri.spring.account.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

/**
 *
 * @author cwoodward
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImage {
    @Id
    private String id;
    
    private String originalFileName;
    private String contentType;
    
    private String accountId;
         
    private Binary image;
}
