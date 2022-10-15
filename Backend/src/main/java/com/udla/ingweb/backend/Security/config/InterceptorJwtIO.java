package com.udla.ingweb.backend.Security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

@Component
public class InterceptorJwtIO implements HandlerInterceptor {

    @Value("#{'${ingweb.jwt.token.auth.path}'.split(',')}")
    private List<String> AUTH_PATH;
    @Value("#{'${ingweb.jwt.excluded.path}'.split(',')}")
    private List<String> excluded;
    @Autowired
    private JwtIO jwtIO;

    public static String token;

    private String nameToken;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean validate=false;

        String uri = request.getRequestURI();

        if (authList(uri)/*uri.equals(AUTH_PATH)*/ || excluded(uri)){
            validate = true;
        }

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            if(key.equals("token")){
                nameToken = value;
            }
        }


        if(!validate && !nameToken.isEmpty()){
            token = nameToken;
            validate = !jwtIO.validateToken(token);
        }

        if(!validate){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return validate;
    }

    private boolean excluded(String path){
        boolean result = false;

        for (String exc:excluded) {
            if (!exc.equals("#") &&  exc.equals(path)){
                return true;
            }
        }
        return result;
    }

    private boolean authList(String path){
        boolean result = false;

        for (String auth:AUTH_PATH) {
            if (auth.equals(path)){
                return true;
            }
        }
        return result;
    }
}
