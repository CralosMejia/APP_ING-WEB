package com.udla.ingweb.backend.Entity.Interfaces;

import com.udla.ingweb.backend.Entity.Store;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AdminStore {
    public Store store;
    public String ID;
}
