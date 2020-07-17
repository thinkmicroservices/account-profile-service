/**
 *
 */
package com.thinkmicroservices.ri.spring.account.profile.controller;

import com.thinkmicroservices.ri.spring.account.profile.model.Profile;
import com.thinkmicroservices.ri.spring.account.profile.service.ProfileDeleteException;

import com.thinkmicroservices.ri.spring.account.profile.service.ProfileService;
import com.thinkmicroservices.ri.spring.account.profile.service.ProfileUpdateException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cwoodward
 */
@RestController
@RequestMapping("profile")
//@CrossOrigin (origins={"*"}, allowedHeaders="*")
@Slf4j

public class AccountProfileController {

    // @Autowired
    //private JWTService jwtService;
    @Autowired
    private ProfileService profileService;

    /**
     *
     * @param emailAddress
     * @return
     */
    @RequestMapping(value = "/find/{emailAddress}", method = {RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "View a list of all registered users", response = List.class)
     @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Profile> findByEmailAddress(@PathVariable String emailAddress) {
        log.debug("find profile By EmailAddress={}", emailAddress);

        Optional<Profile> response = profileService.findByEmailAddress(emailAddress);

        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Profile>(HttpStatus.NO_CONTENT);
        }

    }

    /**
     *
     * @param accountID
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/account/{id}", method = {RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "Get a profile", response = Profile.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Profile> findByAccountId(@PathVariable("id") String accountID, HttpServletRequest httpServletRequest) {
        log.debug("find profile by accountID={}", accountID);

        Optional<Profile> response = profileService.findByAccountId(accountID);
        log.debug("profile response:{}", response);
        if (response.isPresent()) {
            log.debug("response is present");
            //return ResponseEntity.ok().body(response.get());
            return new ResponseEntity<Profile>(response.get(), HttpStatus.OK);
        } else {
            log.debug("response is NOT present");
            return new ResponseEntity<Profile>(HttpStatus.NO_CONTENT);
        }

    }

    /**
     *
     * @param id
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.DELETE)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Profile> deleteProfile(@PathVariable("id") String id, HttpServletRequest httpServletRequest) throws ProfileDeleteException {
        log.debug("deleting profile with id= {}", id);

        profileService.deleteProfile(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @param profile
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.PUT)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Profile> updateProfile(@PathVariable String id, @RequestBody Profile profile, HttpServletRequest httpServletRequest) throws ProfileUpdateException {
        log.debug("update profile = {}", profile);

        return new ResponseEntity<>(profileService.update(profile), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Page<Profile>> getUserProfiles(@ApiParam("The page number to render.") @RequestParam(defaultValue = "0") int pageNo,
            @ApiParam("<Optional> The number of items per page.") @RequestParam(defaultValue = "10") int pageSize,
            @ApiParam("<Optional> field to sort by.") @RequestParam(defaultValue = "id") String sortBy,
            @ApiParam("<Optional> Regex to filter (applied to first,middle and last name fields)- default to all /./") @RequestParam(defaultValue = ".") String like, HttpServletRequest httpServletRequest) {
        log.debug("get user profiles page#={}, pageSize={},sortBy={},like={}", pageNo, pageSize, sortBy, like);

        return new ResponseEntity<>(profileService.findUserProfilesByPage(pageNo, pageSize, sortBy, like), HttpStatus.OK);
    }
}
