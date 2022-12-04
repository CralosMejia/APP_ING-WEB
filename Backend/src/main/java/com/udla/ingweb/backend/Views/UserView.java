package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.UserController;
import com.udla.ingweb.backend.Entity.Interfaces.AdminUser;
import com.udla.ingweb.backend.Entity.Interfaces.Products.ProdUserID;
import com.udla.ingweb.backend.Entity.User;

import com.udla.ingweb.backend.Model.Security.Exceptions.errorMessage;
import com.udla.ingweb.backend.Model.Security.Validators.UserValidator;
import com.udla.ingweb.backend.Model.Security.config.InterceptorJwtIO;
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

    @GetMapping(path = "/rol/{id}")
    public ResponseEntity<?> getUserRol(@PathVariable("id") String id){
        try{
            Map<String, Object> respJson = userController.getUserRol(id);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/find/{id}")
    public ResponseEntity<?> findUser(@PathVariable("id") String id){
        try{
            Map<String, Object> respJson = userController.findUser(id);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping(path = "/addprod")
    public ResponseEntity<?> addProdShoppingCar(@RequestBody ProdUserID params){
        try{
            Map<String, Object> respJson = userController.addProdShoppingCar(params.userId, params.productId,params.amount);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping(value = "/deleteprod")
    public ResponseEntity<?> deleteProdShoppingCar(@RequestBody ProdUserID params){
        try{
            Map<String, Object> respJson = userController.deleteProdShoppingCar(params.userId, params.productId, params.amount);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }

    }

    @PostMapping(path = "/buy")
    public ResponseEntity<?> buy(@RequestBody String UserID){
        try{
            Map<String, Object> respJson = userController.buy(UserID);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/shopping/{id}")
    public ResponseEntity<?> getShoppingCar(@PathVariable("id") String id){
        try{
            Map<String, Object> respJson = userController.getShoppingCar(id);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }




}
