package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.AuthController;
import com.udla.ingweb.backend.Interfaces.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginView {

    @Autowired
    private AuthController login;

    @PostMapping
    public ResponseEntity<?> loginUsers(@RequestBody LoginUser userLogin){

        Map<String, Object> resp = login.loginUser(userLogin.getEmail(), userLogin.getPassword());
        if(resp.containsValue("OK")){
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
        } else if (resp.containsValue("FAIL")) {
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
