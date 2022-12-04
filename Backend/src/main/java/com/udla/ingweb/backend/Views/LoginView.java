package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.AuthController;
import com.udla.ingweb.backend.Model.Security.Validators.AuthValidator;
import com.udla.ingweb.backend.Model.Security.config.JwtIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginView {

    @Autowired
    private AuthController authController;
    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private JwtIO jwtIO;
    @PostMapping()
    public ResponseEntity<?> login(@RequestBody  Map<String, String > paramMap) {
        try{
            authValidator.validate(paramMap);
            Map<String, Object> respJson = authController.generateTokenAndMenu(paramMap.get("email"));
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "token") String token){
        Map<String, Object> respJson = new HashMap<String,Object>();
        try {
            boolean validate=new Boolean(!jwtIO.validateToken(token));
            respJson.put("Validation",validate);
            return  new ResponseEntity<Map<String, Object> >(respJson, HttpStatus.OK);
        }catch (Exception e){
            respJson.put("Validation",false);
            return  new ResponseEntity<Map<String, Object> >(respJson, HttpStatus.OK);

        }

    }
}
