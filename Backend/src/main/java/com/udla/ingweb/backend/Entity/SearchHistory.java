package com.udla.ingweb.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document(collection =  "SearchHistory")
@Getter
@Setter
@RequiredArgsConstructor
public class SearchHistory {

    @Id
    private String id;
    private String userID;
    private String idPurchaseProcess;
    private String paramSearch;
    private Date date = new Date();
    private List<String> searchResult;

    public SearchHistory(String userID, String paramSearch,List<Product> searchResult,String idPurchaseProcess) {
        this.userID = userID;
        this.paramSearch = paramSearch;
        this.idPurchaseProcess = idPurchaseProcess;
        this.searchResult = addSHID(searchResult);
    }

    private List<String> addSHID(List<Product> searchResult){
        List<String> result = new ArrayList<>();
        searchResult.forEach(product -> result.add(product.getId()));
        return result;
    }
}
