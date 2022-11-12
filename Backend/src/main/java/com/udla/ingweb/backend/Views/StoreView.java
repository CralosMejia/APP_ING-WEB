package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.StoreController;
import com.udla.ingweb.backend.Entity.Interfaces.AdminStore;
import com.udla.ingweb.backend.Security.Exceptions.errorMessage;
import com.udla.ingweb.backend.Security.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Stores")
public class StoreView {
    @Autowired
    private StoreController storeController;

    @Autowired
    private UserValidator userValidator;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createStore(@RequestBody AdminStore params){
        try{
            userValidator.validateID(params.ID);
            userValidator.rolValidate(params.ID);
            Map<String, Object> respJson = storeController.createStore(params.store);
            return new ResponseEntity<Map<String, Object>>(respJson, HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/entry")
    public ResponseEntity<?> getStores(@RequestBody String ID){
        try{
            userValidator.validateID(ID);
            userValidator.rolValidate(ID);
            Map<String, Object> respJson = storeController.getStores();
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateStore(@RequestBody AdminStore params,
                                           @PathVariable("id") String id)
    {
        try{
            userValidator.rolValidate(params.ID);
            userValidator.validatePUT(params.store);

            Map<String, Object> respJson = storeController.updateStore(id,params.store);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable("id") String id,
                                           @RequestBody String LoginUserID){
        try{
            userValidator.validateID(LoginUserID);
            userValidator.rolValidate(LoginUserID);
            Map<String, Object> respJson = storeController.deleteStore(id);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }

    }
}
