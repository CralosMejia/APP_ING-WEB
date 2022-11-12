package com.udla.ingweb.backend.Entity.Interfaces;

import com.udla.ingweb.backend.Entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AdminUser {
    public User user;
    public String ID;
}
