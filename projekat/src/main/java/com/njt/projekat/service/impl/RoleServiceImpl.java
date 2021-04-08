package com.njt.projekat.service.impl;

import com.njt.projekat.dao.RoleRepository;
import com.njt.projekat.entity.security.Role;
import com.njt.projekat.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
