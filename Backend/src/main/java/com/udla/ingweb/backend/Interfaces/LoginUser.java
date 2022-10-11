package com.udla.ingweb.backend.Interfaces;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginUser {
    private String email;
    private String password;
}
