package com.thinkmicroservices.ri.spring.account.profile.controller;

import com.thinkmicroservices.ri.spring.account.profile.model.ProfileImage;
import com.thinkmicroservices.ri.spring.account.profile.service.ProfileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author cwoodward
 */
@RestController
@RequestMapping("profile")

@Slf4j
public class AccountProfileImageController {

  
    @Autowired
    private ProfileService profileService;

    /**
     * 
     * @param accountId
     * @param file
     * @param httpServletRequest
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})

    public ResponseEntity<String> updatedProfileImage(@PathVariable("id") String accountId,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest httpServletRequest
    ) throws IOException {

        log.debug("multipart file {}", file);

        log.debug("update profile image {},{}", accountId, file.getOriginalFilename(), file.getContentType());
 
        ProfileImage profileImage = new ProfileImage();
        profileImage.setContentType(file.getContentType());
        profileImage.setOriginalFileName(file.getOriginalFilename());
        profileImage.setAccountId(accountId);
        log.debug("update profile image {}", accountId);

        profileImage.setImage(
                new Binary(BsonBinarySubType.BINARY,
                        profileService.resizeImage(file.getBytes(),
                                200, 200))
        );

        profileImage = profileService.addProfileImage(profileImage);
        log.debug("updated profile image {}", accountId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * 
     * @param accountId
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws IllegalStateException
     * @throws IOException 
     */
    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})

    public void getProfileImage(@PathVariable("id") String accountId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IllegalStateException, IOException {

        log.debug("getProfileImage {}", accountId);

        ProfileImage profileImage = profileService.getProfileImageByAccountId(accountId);
        log.debug("stream image: profileImage {}", profileImage);
        if (profileImage == null) {

            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        httpServletResponse.setContentType(profileImage.getContentType());

        ByteArrayInputStream bais = new ByteArrayInputStream(profileImage.getImage().getData());
        FileCopyUtils.copy(bais, httpServletResponse.getOutputStream());

    }
}
