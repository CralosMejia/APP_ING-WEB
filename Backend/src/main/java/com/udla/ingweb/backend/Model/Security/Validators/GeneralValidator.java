package com.udla.ingweb.backend.Model.Security.Validators;

import com.udla.ingweb.backend.Model.Security.Exceptions.errorMessage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class GeneralValidator {


    public void validate(Map<String,String> paramMap) throws errorMessage {

        if(Objects.isNull(paramMap) ||Objects.isNull(paramMap.get("userID")) ||paramMap.get("userID").isEmpty()){
            message("cliente_id does't exist");
        }
    }

    protected void message(String message) throws errorMessage {
        System.out.println(message);
        throw new errorMessage(message);
    }
}
