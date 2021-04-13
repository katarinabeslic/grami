package com.njt.projekat.dao;

import com.njt.projekat.entity.User;
import com.njt.projekat.entity.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE from UserRole ur where ur.user=?1")
    void deleteByUser(User user);
}
