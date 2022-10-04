package com.udla.ingweb.backend.controller;

import com.udla.ingweb.backend.documents.User;
import com.udla.ingweb.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            User usersave = userRepo.save(user);
            return new ResponseEntity<User>(usersave, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<?> getUsers(){
        try {
            List<User> users = userRepo.findAll();
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id,@RequestBody User user){
        try {
            Optional<User> usersave = userRepo.findById(id);
            user.setId(usersave.get().getId());

            return new ResponseEntity<User>(userRepo.save(user), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id){
        try {
            userRepo.deleteById(id);
            return new ResponseEntity<String>("User has been deleted", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
