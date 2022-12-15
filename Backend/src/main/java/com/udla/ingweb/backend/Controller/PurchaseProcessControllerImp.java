package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.*;
import com.udla.ingweb.backend.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class PurchaseProcessControllerImp implements PurchaseProcessController {

    @Autowired
    private PurchaseProcessRepository ppRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductRepository prodRepo;
    @Autowired
    private RelationProductSearchRepository rpsRepo;
    @Autowired
    private SearchHistoryRepository searchHistoryRepo;


    @Override
    public String obtainIdProcess(String userId){

        if(validateCreationPurchaseProcess(userId)) {
            return createProcess(userId);
        }else {
            return getLastProcessOpen(userId);
        }
    }

    @Override
    public void finishProcess(String userId){
        createRelations(userId);
        PurchaseProcess ppSave =ppRepo.findById(getLastProcessOpen(userId)).get();
        ppSave.setStatus("FINISHED");
        ppRepo.save(ppSave);

    }

    private void createRelations(String userId){
        List<Product> products = userRepo.findById(userId).get().getShoppingCar();

        products.forEach(product -> {
            String idRelation =getIdRelationForProduct(product.getId());
            addParameterForProduct(userId,product.getId(),idRelation);
        });



    }

    //REVISAR
    private void addParameterForProduct(String userId, String productId,String relationId){
        String idProcess = getLastProcessOpen(userId);
        RelationProductSearch rpsSave = rpsRepo.findById(relationId).get();
        List<SearchHistory> shSave =searchHistoryRepo.findAll().stream().filter(sh -> sh.getIdPurchaseProcess().equals(idProcess) && sh.getSearchResult().contains(productId)).toList();


        shSave.forEach(searchHistory -> {
            List<String> searchParameterList = searchHistory.getSearchResult();
            if(searchParameterList.contains(productId) && validateSearchParam(searchHistory.getParamSearch(),relationId)){
                rpsSave.addParam(searchHistory.getParamSearch());
                rpsRepo.save(rpsSave);
            }

        });

    }

    private boolean validateSearchParam(String searchParam, String relationId){
        RelationProductSearch rpsSave = rpsRepo.findById(relationId).get();
        if(rpsSave.getListSearchParams().contains(searchParam)){
            return false;
        }
        return true;
    }
    private String getIdRelationForProduct(String prodId){
        Optional<RelationProductSearch> rpsSave = rpsRepo.findAll().stream().filter(rps -> rps.getIdProduct().equals(prodId)).findFirst();

        if(rpsSave.isEmpty()){
            RelationProductSearch rpsNew = new RelationProductSearch(prodId);
            rpsRepo.save(rpsNew);
            return rpsNew.getId();
        }

        return rpsSave.get().getId();
    }

    private String createProcess(String userId){
        PurchaseProcess ppSave= new PurchaseProcess(userId);
        ppRepo.save(ppSave);
        return ppSave.getId();
    }

    private String getLastProcessOpen(String userId){
        PurchaseProcess pp = ppRepo.findAll().stream().filter(purchaseProcess -> purchaseProcess.getUserID().equals(userId) && purchaseProcess.getStatus().equals("INPROCESS")).findFirst().get();
        return pp.getId();
    }

    private boolean validateCreationPurchaseProcess(String idUser) {
        Optional<PurchaseProcess> pp =ppRepo.findAll().stream().filter(purchaseProcess -> purchaseProcess.getUserID().equals(idUser) && purchaseProcess.getStatus().equals("INPROCESS")).findFirst();

        if (pp.isEmpty()){
            return true;
        }

        return false;
    }


}
