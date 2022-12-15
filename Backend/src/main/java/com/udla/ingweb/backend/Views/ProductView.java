package com.udla.ingweb.backend.Views;

import com.udla.ingweb.backend.Controller.ProductController;
import com.udla.ingweb.backend.Entity.Interfaces.Products.AdminProduct;
import com.udla.ingweb.backend.Entity.Interfaces.Sh.Sh;
import com.udla.ingweb.backend.Model.Security.Exceptions.errorMessage;
import com.udla.ingweb.backend.Model.Security.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Produtcs")
public class ProductView {

    @Autowired
    private ProductController productController;

    @Autowired
    private UserValidator userValidator;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createProduct(@RequestBody AdminProduct params){
        try{
            userValidator.validateID(params.ID);
            userValidator.rolValidate(params.ID);
            Map<String, Object> respJson = productController.createProduct(params.product);
            return new ResponseEntity<Map<String, Object>>(respJson, HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/entryCustomer")
    public ResponseEntity<?> getProducts(){
        try{
            Map<String, Object> respJson = productController.getProductsCustomer();
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody AdminProduct params,
                                        @PathVariable("id") String id)
    {
        try{
            userValidator.rolValidate(params.ID);
            userValidator.validatePUT(params.product);

            Map<String, Object> respJson = productController.updateProduct(id,params.product);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id,
                                        @RequestBody String LoginUserID){
        try{
            userValidator.validateID(LoginUserID);
            userValidator.rolValidate(LoginUserID);
            Map<String, Object> respJson = productController.deleteProduct(id);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (errorMessage e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }

    }


    @PostMapping (path = "/find")
    public ResponseEntity<?> findProducts(@RequestBody Sh findParam){
        try{
            Map<String, Object> respJson = productController.findProducts(findParam.getParam(), findParam.getUserId());
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/entryStore/{id}")
    public ResponseEntity<?> getProductsForStore(@PathVariable("id") String id){
        try{
            Map<String, Object> respJson = productController.getProductsStore(id);
            return new ResponseEntity<Map<String, Object>>(respJson,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
}
