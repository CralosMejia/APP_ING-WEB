package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.UserController;
import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Interfaces.LoginUser;
import com.udla.ingweb.backend.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Users")
public class UserView {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserController userController;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        Map<String, Object> resp = userController.createUser(user);
        if(resp.containsValue("OK")) {
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<String>("Error to create User", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestHeader(value = "token") String token){
        Map<String, Object> resp = userController.getUsers(token);
        if(resp.containsValue("OK")) {
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
        } else if (resp.containsValue("You can't delete yourself")) {
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<String>("Error to get users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id,@RequestBody User user,@RequestHeader(value = "token") String token){
        Map<String, Object> resp= userController.updateUser(id,user,token);
        if(resp.containsValue("OK")) {
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Error to update User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id,@RequestHeader(value = "token") String token){
        Map<String, Object> resp = userController.deleteUser(id,token);
        if(resp.containsValue("OK") ){
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Error to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
