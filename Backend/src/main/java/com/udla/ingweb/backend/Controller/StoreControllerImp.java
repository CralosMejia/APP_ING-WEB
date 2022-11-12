package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.Store;
import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.StoreReposiroty;
import com.udla.ingweb.backend.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class StoreControllerImp implements StoreController {
    @Autowired
    private StoreReposiroty storeRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public Map<String, Object> createStore(Store store) {
        Map<String, Object> respJson = new HashMap<String,Object>();

        Optional<User> usersave = userRepo.findById(store.getOwner().getId());

        store.setOwner(usersave.get());

        Store storesave = storeRepo.save(store);


        respJson.put("Status","OK");
        respJson.put("Store",storesave);

        return respJson;
    }

    @Override
    public Map<String, Object> getStores() {
        Map<String, Object> respJson = new HashMap<String,Object>();

        List<Store> stores= new ArrayList<Store>(storeRepo.findAll());

        respJson.put("Stores",stores);

        return respJson;
    }

    @Override
    public Map<String, Object> updateStore(String id, Store store) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        Optional<Store> storesave = storeRepo.findById(id);

        if(!Objects.isNull(store.getName())){
            storesave.get().setName(store.getName());
        }
        if(!Objects.isNull(store.getAddress())){
            storesave.get().setAddress(store.getAddress());
        }
        if(!Objects.isNull(store.getOwner())){
            storesave.get().setOwner(store.getOwner());
        }
        storeRepo.save(storesave.get());

        respJson.put("Status","OK");
        respJson.put("Store",storesave);

        return respJson;
    }

    @Override
    public Map<String, Object> deleteStore(String id) {
        Map<String, Object> respJson = new HashMap<String,Object>();

        storeRepo.deleteById(id);
        respJson.put("Status","OK");
        respJson.put("MSG","Store has been deleted");
        return respJson;
    }
}
