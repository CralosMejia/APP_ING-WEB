package com.udla.ingweb.backend.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udla.ingweb.backend.Entity.Product;
import com.udla.ingweb.backend.Entity.SearchHistory;
import com.udla.ingweb.backend.Entity.Store;
import com.udla.ingweb.backend.Model.ProductRepository;
import com.udla.ingweb.backend.Model.StoreReposiroty;
import com.udla.ingweb.backend.Util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import software.amazon.awssdk.services.kms.KmsClient;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

@Controller
public class ProductControllerImp implements ProductController {

    @Autowired
    private  EncryptionService encryptionService;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private PurchaseProcessController ppControl;
    @Autowired
    private SearchHistoryController shController;

    @Autowired
    private OpenSSLUtil openSSLUtil;


    @Override
    public Map<String, Object> createProduct(Product product) {

        Map<String, Object> respJson = new HashMap<String,Object>();
        Product productSave = productRepo.save(product);

//        Optional<Store> storesave = storeRepo.findById(product.getStoreID());
//        storesave.get().addProduct(product);
//        storeRepo.save(storesave.get());


        respJson.put("Status","OK");
        respJson.put("Product",productSave);

        return respJson;
    }

    @Override
    public Map<String, Object> getProductsCustomer() {
        Map<String, Object> respJson = new HashMap<String,Object>();

        List<Product> products= new ArrayList<Product>(productRepo.findAll());

        products.removeIf(product ->
                (product.getAmount() == 0));

        respJson.put("Products",products);

        return respJson;
    }

    @Override
    public Map<String, Object> getProductsStore(String idStore) {
        Map<String, Object> respJson = new HashMap<String,Object>();

        List<Product> products= new ArrayList<Product>(productRepo.findAll());

        products.removeIf(product ->
                (!product.getStoreID().equals(idStore)));

        respJson.put("Products",products);

        return respJson;
    }

    //Ecriptado
    @Override
    public Map<String, Object> getHealthProducts() throws Exception {
        Map<String, Object> respJson = new HashMap<String,Object>();
        String clave = "UDLA2023";
        String iv="1234567890123456";



        List<Product> products= new ArrayList<Product>(productRepo.findAll());

        products.removeIf(product ->
                (Objects.isNull(product.getCategoria()) || !product.getCategoria().equals("SALUD")));
        try{
        // Convertir la clave y el IV de cadenas de texto a bytes
        //byte[] claveBytes = ClaveUtils.generarClave(clave);
       // byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);

        // Crear una instancia de la clase Cipher con el modo y el algoritmo adecuados
        //Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Crear una clave secreta a partir de los bytes de la clave
        //SecretKeySpec secretKeySpec = new SecretKeySpec(claveBytes, "AES");

        // Crear un objeto IvParameterSpec a partir de los bytes del IV
        //IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        // Configurar el cifrado en modo de encriptación y con la clave y el IV adecuados
        //cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        // Convertir la lista de objetos a una cadena JSON
        String datosJson = convertirListaObjetosAJson(products);

        String response = encryptionService.encryptData(datosJson);
        // Encriptar los datos
        //byte[] datosEncriptados = cipher.doFinal(datosJson.getBytes(StandardCharsets.UTF_8));

        // Codificar los datos encriptados en formato Base64
        //String datosEncriptadosBase64 = Base64.getEncoder().encodeToString(datosEncriptados);

        //System.out.println(verificarEncriptacion(clave,iv,datosEncriptadosBase64));
        respJson.put("Products",response);

        return respJson;
    } catch (Exception e) {
        e.printStackTrace();
        return respJson;
    }

    }

    private static String convertirListaObjetosAJson(List<Product> datos) {
        // Crear una instancia de Gson
        Gson gson = new GsonBuilder().create();

        // Convertir la lista de objetos a una cadena JSON
        String datosJson = gson.toJson(datos);

        return datosJson;
    }

    public static boolean verificarEncriptacion(String clave, String iv, String datosEncriptados) {
        try {


            // Convertir la clave y el IV de cadenas de texto a bytes
            byte[] claveBytes = ClaveUtils.generarClave(clave);
            byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);

            // Decodificar los datos encriptados desde Base64
            byte[] datosEncriptadosBytes = Base64.getDecoder().decode(datosEncriptados);

            // Crear una instancia de la clase Cipher con el modo y el algoritmo adecuados
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Crear una clave secreta a partir de los bytes de la clave
            SecretKeySpec secretKeySpec = new SecretKeySpec(claveBytes, "AES");

            // Crear un objeto IvParameterSpec a partir de los bytes del IV
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

            // Configurar el cifrado en modo de desencriptación y con la clave y el IV adecuados
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            // Desencriptar los datos
            byte[] datosDesencriptados = cipher.doFinal(datosEncriptadosBytes);

            // Convertir los datos desencriptados a una cadena de texto
            String datosDesencriptadosStr = new String(datosDesencriptados, StandardCharsets.UTF_8);


            // Verificar si la desencriptación fue exitosa
            return datosDesencriptadosStr != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Map<String, Object> findProducts(String findParam,String userId) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        List<String> splited = Arrays.stream(findParam.split(" ")).toList();
        List<Product> products= new ArrayList<Product>(productRepo.findAll()) ;
        List<Product> result = new ArrayList<>();

        products.removeIf(product ->
                (product.getAmount() == 0));

        if(!findParam.equals("allproductstobuy")){
            splited.forEach(s -> {
                products.forEach(product -> {
                    if(product.getName().toLowerCase().contains(s.toLowerCase()) || product.getDescription().contains(s.toLowerCase())){

                        if(!result.stream().anyMatch(product1 -> product1.getId().equals(product.getId()))){
                            result.add(product);
                        }
                    }
                });
            });
        }

        if(!result.isEmpty()){
            String idProcess = ppControl.obtainIdProcess(userId);
            SearchHistory sh = new SearchHistory(userId,findParam,result,idProcess);
            shController.createSearchHistory(sh);
        }

        respJson.put("Products",result);
        return respJson;
    }

    @Override
    public Map<String, Object> updateProduct(String id, Product product) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        Optional<Product> productsave = productRepo.findById(id);

        if(!Objects.isNull(product.getName())){
            productsave.get().setName(product.getName());
        }
        if(!Objects.isNull(product.getDescription())){
            productsave.get().setDescription(product.getDescription());
        }
        if(product.getPrice() != 0){
            productsave.get().setPrice(product.getPrice());
        }
        if(product.getAmount() != 0){
            productsave.get().setAmount(product.getAmount());
        }
        if(!Objects.isNull(product.getCategoria())){
            productsave.get().setCategoria(product.getCategoria());
        }
        productRepo.save(productsave.get());

        respJson.put("Status","OK");
        respJson.put("Product",productsave);

        return respJson;
    }

    @Override
    public Map<String, Object> deleteProduct(String id) {
        Map<String, Object> respJson = new HashMap<String,Object>();

        productRepo.deleteById(id);
        respJson.put("Status","OK");
        respJson.put("MSG","Product has been deleted");
        return respJson;
    }
}
