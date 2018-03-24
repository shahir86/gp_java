package com.gp.controller;

import com.gp.db.bo.User;
import com.gp.db.bo.UserObj;
import com.gp.repository.UserRepository;
import com.gp.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/user/all")
    public List<UserObj> getAllUsers() {    	
        return userRepo.findAll();
    }


    @GetMapping("/user/search/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String emailAdd) {
        return userRepo.findByEmail(emailAdd);
    }

    

    
//    @PutMapping("/user/update/{email}")
//    public ResponseEntity updateUser(@PathVariable(value = "email") String email, @RequestParam Map<String, String> body) throws NoSuchAlgorithmException {
//    
//    	System.out.println(email+"---" + body.get("password"));
//        User usr = userRepo.findByEmail(email);
//        usr.setPassword(passwordEncoder(body.get("password")));
//        usr.setUserName(body.get("userName"));
//        usr.setUpdatedDate(DateUtils.getCurrentTime());
//
//        User updatedUsr = userRepo.createOrUpdateUsr(usr);
//        return new ResponseEntity("SUCCESSFULLY UPDATED",HttpStatus.CREATED);
//    }
    
    @PutMapping("/user/update/{email}")
    public ResponseEntity<User> updateUser(@PathVariable("email") String email, @RequestBody User user) throws NoSuchAlgorithmException {
        System.out.println("Updating User " + email);
         
        User usr = userRepo.findByEmail(email);
         
        if (usr==null) {
            System.out.println("User with id " + email + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
        usr.setPassword(userRepo.passwordEncoder(user.getPassword()));
        usr.setUserName(user.getUserName());
        usr.setUpdatedDate(DateUtils.getCurrentTime());
         
        userRepo.createOrUpdateUsr(usr);
        return new ResponseEntity<User>(usr, HttpStatus.OK);
    }
    

    
    @PostMapping("/user/new")
    public ResponseEntity createUser(@RequestBody User user) throws NoSuchAlgorithmException {
        System.out.println("Creating User " + user.getEmailAddress());
 
        if (userRepo.isUserExist(user)) {
            System.out.println("A User with email " + user.getEmailAddress() + " already exist");
            return new ResponseEntity("A User with email " + user.getEmailAddress() + " already exist",HttpStatus.CONFLICT);
        }
 
   	 	User usr = new User();
   	 	usr.setEmailAddress(user.getEmailAddress());
   	 	usr.setCreatedDate(DateUtils.getCurrentTime());
   	 	usr.setPassword(userRepo.passwordEncoder(user.getPassword()));
   	 	usr.setUserName(user.getUserName());
   	 	usr.setUpdatedDate(DateUtils.getCurrentTime());
   	 
        userRepo.createOrUpdateUsr(usr);
 
        return new ResponseEntity<User>(usr, HttpStatus.CREATED);
    }


	@DeleteMapping("/user/del/{email}")
	public ResponseEntity deleteUser(@PathVariable String email) {
		
		
		if(userRepo.deleteByEmail(email)){
			return new ResponseEntity(email + " SUCCESSFULLY DELETED", HttpStatus.OK);
		} 
		
			
		return new ResponseEntity(email + " FAIL TO DELETE", HttpStatus.NOT_FOUND);

	}
	
	@PostMapping("/login")
    public String loginForm(@RequestBody User user) {
        if (!userRepo.loginSuccess(user.getEmailAddress(),user.getPassword())) {
            return "Invalid Login";
        }
        return "Successfully login";
    }


}
