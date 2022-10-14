package com.udla.ingweb.backend.Controller;

import java.util.Map;

public interface AuthController {


    public Map<String, Object> generateToken(String userID);
}
