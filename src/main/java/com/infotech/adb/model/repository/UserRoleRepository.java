package com.infotech.adb.model.repository;

import com.infotech.adb.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT username as username, role as authority from UserRole WHERE username = :username")
    Collection<? extends GrantedAuthority> findRoleByUsername(@Param("username") String username);
}
