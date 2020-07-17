package com.thinkmicroservices.ri.spring.account.profile.repository;

import com.thinkmicroservices.ri.spring.account.profile.model.Profile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author cwoodward
 */
public interface ProfileRepository extends MongoRepository<Profile, String> {

    /**
     *
     * @param accountId
     * @return
     */
    Optional<Profile> findByAccountId(String accountId);

    /**
     *
     * @param email
     * @return
     */
    Optional<Profile> findByEmail(String email);

    /**
     *
     * @param like
     * @param paging
     * @return
     */
    @Query(value = "{$or:[{firstName:{$regex:?0,$options:'i'}},{middleName:{$regex:?0,$options:'i'}},{lastName:{$regex:?0,$options:'i'}},{email:{$regex:?0,$options:'i'}}]}")

    Page<Profile> findByQuery(String like, Pageable paging);
}
