package com.udla.ingweb.backend.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection =  "RelationProductSearch")
@Getter
@Setter
public class RelationProductSearch {
    @Id
    private String id;
    private String idProduct;
    private List<String> listSearchParams = new ArrayList<>();

    public RelationProductSearch(String idProduct) {
        this.idProduct = idProduct;
    }

    public void addParam(String param){
        listSearchParams.add(param);
    }
}
