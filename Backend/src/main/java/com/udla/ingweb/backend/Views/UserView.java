package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.UserController;
import com.udla.ingweb.backend.Entity.User;

import com.udla.ingweb.backend.Security.Exceptions.errorMessage;
import com.udla.ingweb.backend.Security.Validators.GeneralValidator;
import com.udla.ingweb.backend.Security.Validators.UserValidator;
import com.udla.ingweb.backend.Security.config.InterceptorJwtIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/Users")
public class UserView {

    @Autowired
    private UserController userController;
    @Autowired
    private GeneralValidator generalValidator;

    @Autowired
    private UserValidator userValidator;



    @PostMapping(path = "/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> createUser(@RequestBody User user,@RequestParam("grant_type") String grantType){
        try{
            userValidator.validate(user,grantType);
            Map<String, Object> respJson = userController.createUser(user);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE )
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> getUsers(@RequestBody MultiValueMap<String,String> paramMap,
                                      @RequestParam("grant_type") String grantType){
        try{
            userValidator.validateUserID(paramMap.getFirst("userID"));
            generalValidator.validate(paramMap, grantType);
            Map<String, Object> respJson = userController.getUsers(InterceptorJwtIO.token);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(value = "/update/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> updateUser(@RequestBody User user,
                                        @RequestParam("grant_type") String grantType,
                                        @PathVariable("id") String id)
    {
        try{
            userValidator.validatePUT(user,grantType);
            userValidator.validateUserID(id);
            Map<String, Object> respJson = userController.updateUser(id,user);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id,
                                        @RequestParam("grant_type") String grantType){
        try{
            userValidator.validateDelete(grantType);
            userValidator.validateUserID(id);
            userValidator.validateDeleteUser(id);
            Map<String, Object> respJson = userController.deleteUser(id);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }

    }




}
