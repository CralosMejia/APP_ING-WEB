package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.SearchHistory;
import com.udla.ingweb.backend.Model.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchHistoryControllerImp implements SearchHistoryController{

    @Autowired
    private SearchHistoryRepository searchHistoryRepo;

    @Override
    public Map<String, Object> createSearchHistory(SearchHistory searchHistory) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        SearchHistory shSave=searchHistoryRepo.save(searchHistory);
        respJson.put("SearchHistory",shSave);
        return respJson;
    }
}
