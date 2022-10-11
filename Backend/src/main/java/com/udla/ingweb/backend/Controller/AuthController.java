package com.udla.ingweb.backend.Controller;

import java.util.Map;

public interface AuthController {

    public Map<String, Object> loginUser(String email, String password);
}
