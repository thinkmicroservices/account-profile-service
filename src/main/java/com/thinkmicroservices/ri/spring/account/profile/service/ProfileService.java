package com.thinkmicroservices.ri.spring.account.profile.service;

import com.thinkmicroservices.ri.spring.account.profile.messaging.ProfileQueueManager;
import com.thinkmicroservices.ri.spring.account.profile.messaging.events.ProfileCreatedEvent;
import com.thinkmicroservices.ri.spring.account.profile.messaging.events.ProfileImageCreatedEvent;
import com.thinkmicroservices.ri.spring.account.profile.messaging.events.ProfileImageUpdatedEvent;
import com.thinkmicroservices.ri.spring.account.profile.messaging.events.ProfileUpdatedEvent;
import com.thinkmicroservices.ri.spring.account.profile.model.Profile;
import com.thinkmicroservices.ri.spring.account.profile.model.ProfileImage;
import com.thinkmicroservices.ri.spring.account.profile.repository.ProfileImageRepository;
import com.thinkmicroservices.ri.spring.account.profile.repository.ProfileRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 *
 * @author cwoodward
 */
@Service
@Slf4j
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    // autowire the outgoing account event message queue
    @Autowired

    private ProfileQueueManager profileQueueManager;
    private Counter profileCreatedCounter;
    private Counter profileUpdatedCounter;
    private Counter profileDeletedCounter;

    /**
     *
     * @param profile
     * @return
     * @throws ProfileCreateException
     */
    public Profile create(Profile profile) throws ProfileCreateException {
        log.debug("create new Profile={}", profile);
        profileRepository.save(profile);
        log.debug("created Profile={}", profile);
        log.debug("broadcast profile created event");

        this.profileQueueManager.outboundEvents()
                .send(MessageBuilder.withPayload(new ProfileCreatedEvent(profile.getAccountId(), profile.getEmail()))
                        .setHeader("type", "PROFILE_CREATED_EVENT").build());
        log.debug("profile created event broadcasted");
       
        // capture metrics
        profileCreatedCounter.increment(1.0); 
        return profile;
    }

    /**
     *
     * @param id
     * @return
     */
    public Optional<Profile> findById(String id) {
        log.debug("findById ={}", id);
        return profileRepository.findById(id);
    }

    /**
     *
     * @param emailAddress
     * @return
     */
    public Optional<Profile> findByEmailAddress(String emailAddress) {
        log.debug("find by email address ={}", emailAddress);

        return profileRepository.findByEmail(emailAddress);
    }

    /**
     *
     * @param accountId
     * @return
     */
    public Optional<Profile> findByAccountId(String accountId) {
        log.debug("find by accountId ={}", accountId);

        return profileRepository.findByAccountId(accountId);
    }

    /**
     *
     * @param profile
     * @return
     * @throws ProfileUpdateException
     */
    public Profile update(Profile profile) throws ProfileUpdateException {
        log.debug("update profile {}", profile);
        Profile updatedProfile = profileRepository.save(profile);
        log.debug("broadcast profile updated event");
        this.profileQueueManager.outboundEvents()
                .send(MessageBuilder.withPayload(new ProfileUpdatedEvent(profile.getAccountId(), profile.getEmail()))
                        .setHeader("type", "PROFILE_UPDATED_EVENT").build());
        log.debug("profile updated event broadcasted");
        // capture metrics
        profileUpdatedCounter.increment(1.0); 
        return updatedProfile;

    }

    /**
     *
     * @param id
     * @throws ProfileDeleteException
     */
    public void deleteProfile(String id) throws ProfileDeleteException {
        
        log.debug("delete profile id={} ", id);
        profileRepository.deleteById(id);
        profileDeletedCounter.increment(1.0);
        
        
    }

    /**
     *
     * @param profileImage
     * @return
     */
    public ProfileImage addProfileImage(ProfileImage profileImage) {

        log.debug("addProfileImage", profileImage);

        // lookup the profile
        Optional<Profile> existingProfile = profileRepository.findByAccountId(profileImage.getAccountId());
        Optional<ProfileImage> existingProfileImage = profileImageRepository.findByAccountId(profileImage.getAccountId());

        if (existingProfile.isPresent()) {
            if (!existingProfileImage.isPresent()) {
                log.debug("create new profile image");
                ProfileImage newProfileImage = profileImageRepository.insert(profileImage);

                this.profileQueueManager.outboundEvents()
                        .send(MessageBuilder.withPayload(new ProfileImageCreatedEvent(profileImage.getAccountId(), null))
                                .setHeader("type", "PROFILE_IMAGE_CREATED_EVENT").build());
                return profileImage;
            } else {

                // do an update
                log.debug("update profile image");
                profileImage.setId(existingProfileImage.get().getId());

                ProfileImage updatedProfileImage = profileImageRepository.save(profileImage);
                this.profileQueueManager.outboundEvents()
                        .send(MessageBuilder.withPayload(new ProfileImageUpdatedEvent(profileImage.getAccountId()))
                                .setHeader("type", "PROFILE_IMAGE_UPDATED_EVENT").build());
                return updatedProfileImage;
            }
        } else {
            // no profile existing for the supplied account id
            log.error("could not find profile for {}", profileImage.getAccountId());
            return null;
        }

    }

    public byte[] resizeImage(byte[] imageArray, int scaledWidth, int scaledHeight) {

        InputStream bais = new ByteArrayInputStream(imageArray);
        try {
            BufferedImage inputImage = ImageIO.read(bais);

            Image tmp = inputImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

            BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(resizedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }

        return imageArray; // ok... could resize it 

    }

    /**
     *
     * @param accountId
     * @return
     */
    public ProfileImage getProfileImageByAccountId(String accountId) {
        log.debug("getProfileImageByAccountId={}", accountId);
        Optional<ProfileImage> result = profileImageRepository.findByAccountId(accountId);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public Page<Profile> findUserProfilesByPage(int pageNo, int pageSize, String sortBy, String like) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Profile> result = this.profileRepository.findByQuery(like, paging);

        return result;
    }

    /**
     * initialize the service metrics
     */
    @PostConstruct
    private void initializeMetrics() {
        profileCreatedCounter = Counter.builder("account.profile.created")
              
                .description("The number of account profiles created.")
                .register(meterRegistry);
        
         profileUpdatedCounter = Counter.builder("account.profile.updated")
                             .description("The number of account profiles updated.")
                .register(meterRegistry);
         
          profileDeletedCounter = Counter.builder("account.profile.deleted")
            
                .description("The number of account profiles deleted.")
                .register(meterRegistry);

    }
}
