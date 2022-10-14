package com.udla.ingweb.backend.Security.Validators;

import com.udla.ingweb.backend.Security.Exceptions.errorMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Component
public class GeneralValidator {

    private static final String CLIENT_CREDENTIALS ="client_credential";

    public void validate(MultiValueMap<String,String> paramMap, String grantType) throws errorMessage {
        if(!grantType.equals(CLIENT_CREDENTIALS)){
            message("Field grantType is invalid");
        }

        if(Objects.isNull(paramMap) ||Objects.isNull(paramMap.getFirst("userID")) ||paramMap.getFirst("userID").isEmpty()){
            message("cliente_id does't exist");
        }
    }

    protected void message(String message) throws errorMessage {
        System.out.println(message);
        throw new errorMessage(message);
    }
}
