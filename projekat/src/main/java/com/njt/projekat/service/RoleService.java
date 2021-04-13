package com.njt.projekat.service;

import com.njt.projekat.entity.security.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findByName(String name);
}
