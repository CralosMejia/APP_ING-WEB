package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.SearchHistory;
import com.udla.ingweb.backend.Entity.User;

import java.util.Date;
import java.util.Map;

public interface SearchHistoryController {

    public Map<String, Object> createSearchHistory(SearchHistory searchHistory);

}
