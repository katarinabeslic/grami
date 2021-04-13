package com.njt.projekat.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import com.njt.projekat.dao.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njt.projekat.dao.RoleRepository;
import com.njt.projekat.dao.UserRepository;
import com.njt.projekat.entity.User;
import com.njt.projekat.entity.security.Role;
import com.njt.projekat.entity.security.UserRole;
import com.njt.projekat.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public User createUser(User u, List<String> roles) {
		User user = findByUsername(u.getUsername());
		if (user != null) {
			return user;
		}
		
		user = new User(u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhoneNumber(), u.getUsername(), u.getPassword());
		Set<UserRole> userRoles = new HashSet<>();
		for (String roleName : roles) {
			Role role = roleRepository.findByName(roleName);
			if (role == null) {
				role = new Role(roleName);
				roleRepository.save(role);
			}
			userRoles.add(new UserRole(user, role));
		}
		user.setUserRoles(userRoles);
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public void deleteUserRoles(User user) {
		userRoleRepository.deleteByUser(user);
	}

}
