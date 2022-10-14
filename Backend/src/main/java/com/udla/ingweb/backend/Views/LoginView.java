package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.AuthController;
import com.udla.ingweb.backend.Security.Exceptions.errorMessage;
import com.udla.ingweb.backend.Security.Validators.AuthValidator;
import com.udla.ingweb.backend.Security.Validators.GeneralValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginView {

    @Autowired
    private AuthController authController;
    @Autowired
    private AuthValidator authValidator;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> login(@RequestBody MultiValueMap<String,String> paramMap, @RequestParam("grant_type") String grantType) {
        try{
            authValidator.validate(paramMap, grantType);
            Map<String, Object> respJson = authController.generateToken(paramMap.getFirst("email"));
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
}
