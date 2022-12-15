package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.SaleController;
import com.udla.ingweb.backend.Entity.Interfaces.sales.SalesIds;
import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.Security.Exceptions.errorMessage;
import com.udla.ingweb.backend.Model.Security.Validators.UserValidator;
import com.udla.ingweb.backend.Model.Security.config.InterceptorJwtIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/Sales")
public class SalesView {

    @Autowired
    private SaleController salesController;

    @Autowired
    private UserValidator userValidator;


    @GetMapping(path = "/entry/{id}")
    public ResponseEntity<?> getSales(@PathVariable String id){
        try{
            userValidator.validateID(id);
            Map<String, Object> respJson = salesController.getSales(id);
            return new ResponseEntity<Map<String, Object>>(respJson, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping(path = "/prod")
    public ResponseEntity<?> getSalesProd(@RequestBody SalesIds ids){
        try{
            userValidator.validateID(ids.getIdUser());
            userValidator.rolValidateSeller(ids.getIdUser());
            Map<String, Object> respJson = salesController.getSalesProd(ids.getIdProd());
            return new ResponseEntity<Map<String, Object>>(respJson, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/relation/{id}")
    public ResponseEntity<?> getRelation(@PathVariable String id){
        try{
            Map<String, Object> respJson = salesController.getRelation(id);
            return new ResponseEntity<Map<String, Object>>(respJson, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping(path = "/best-selling")
    public ResponseEntity<?> getBestSellingProduct(@RequestBody String date){
        try{
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date datea =formato.parse(date);
            Map<String, Object> respJson = salesController.getBestSellingProduct(datea);
            return new ResponseEntity<Map<String, Object>>(respJson, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

}
