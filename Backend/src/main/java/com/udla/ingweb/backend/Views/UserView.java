package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.UserController;
import com.udla.ingweb.backend.Entity.Interfaces.AdminUser;
import com.udla.ingweb.backend.Entity.User;

import com.udla.ingweb.backend.Security.Exceptions.errorMessage;
import com.udla.ingweb.backend.Security.Validators.UserValidator;
import com.udla.ingweb.backend.Security.config.InterceptorJwtIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/Users")
public class UserView {

    @Autowired
    private UserController userController;
    @Autowired
    private UserValidator userValidator;


    @PostMapping(path = "/create")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            userValidator.validate(user);
            Map<String, Object> respJson = userController.createUser(user);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping(path = "/entry")
    public ResponseEntity<?> getUsers(){
        try{
            Map<String, Object> respJson = userController.getUsers(InterceptorJwtIO.token);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody AdminUser params,
                                        @PathVariable("id") String id)
    {
        try{
            userValidator.validateID(params.ID);
            userValidator.rolValidate(params.ID);
            userValidator.validatePUT(params.user);
            userValidator.validateID(id);

            Map<String, Object> respJson = userController.updateUser(id,params.user);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id,
                                        @RequestBody String LoginUserID){
        try{
            userValidator.validateID(LoginUserID);
            userValidator.rolValidate(LoginUserID);
            userValidator.validateID(id);
            userValidator.validateDeleteUser(id);
            Map<String, Object> respJson = userController.deleteUser(id);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }

    }




}
