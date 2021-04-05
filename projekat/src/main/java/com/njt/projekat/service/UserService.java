package com.njt.projekat.service;

import java.util.List;

import com.njt.projekat.entity.User;

public interface UserService {

	User findByUsername(String username);

	User findByEmail(String email);

	User save(User user);

	User createUser(User user, List<String> roles);

    List<User> findAll();

    void deleteById(int id);

    User findById(int id);
}
