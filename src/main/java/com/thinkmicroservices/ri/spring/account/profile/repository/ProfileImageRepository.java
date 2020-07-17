package com.thinkmicroservices.ri.spring.account.profile.repository;

import com.thinkmicroservices.ri.spring.account.profile.model.ProfileImage;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author cwoodward
 */
public interface ProfileImageRepository extends MongoRepository<ProfileImage, String> {

    /**
     *
     * @param accountId
     * @return
     */
    Optional<ProfileImage> findByAccountId(String accountId);

}
