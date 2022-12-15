package com.udla.ingweb.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection =  "PurchaseProcess")
@Getter
@Setter
@RequiredArgsConstructor
public class PurchaseProcess {

    @Id
    private String id;
    private String userID;
    private String status;


    public PurchaseProcess(String userID) {
        this.userID = userID;
        this.status = "INPROCESS";
    }

}
