package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.Store;

import java.util.Map;

public interface StoreController {

    public Map<String, Object> createStore(Store store);
    public Map<String, Object> getStores();
    public Map<String, Object> getStoresForSeller(String userId);

    public Map<String, Object>  updateStore(String id, Store store);
    public Map<String, Object>  findStore(String storeId);

    public Map<String, Object> deleteStore(String id);

}
