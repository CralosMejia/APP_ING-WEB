package com.udla.ingweb.backend.Security.config;


import com.udla.ingweb.backend.Security.Utils.GsonUtils;
import io.fusionauth.jwt.JWTUtils;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Component
public class JwtIO {

    @Value("${ingweb.jwt.token.secret:secret}")
     private String SECRET;
    @Value("${ingweb.jwt.token.expiresIn:3600}")
    private int EXPIRE_IN;


    public String generateToken(Object src){
        String subject = GsonUtils.serializae(src);
        //buil an HMCA signer used SHA-256
        Signer signer = HMACSigner.newSHA256Signer(SECRET);
        TimeZone tz = TimeZone.getDefault();
        ZonedDateTime zdt = ZonedDateTime.now(tz.toZoneId()).plusSeconds(EXPIRE_IN);

        JWT jwt = new JWT().setSubject(subject).setExpiration(zdt);
        return JWT.getEncoder().encode(jwt,signer);
    }

    public boolean validateToken(String encodedJWT){

        JWT jwt =jwt(encodedJWT);

        return jwt.isExpired();
    }

    public String getPayload(String encodedJWT){

        JWT jwt =jwt(encodedJWT);
        String res = jwt.subject;
        return res.replaceAll("\"","");
    }

    //private JWT jwt(String encodedJWT){

      //  Verifier verfifier = HMACVerifier.newVerifier(SECRET);

        //return  JWT.getDecoder().decode(encodedJWT,verfifier);
    //}

    private JWT jwt(String encodedJWT){
        JWT jwt = JWTUtils.decodePayload(encodedJWT);
        return jwt;

    }

}
