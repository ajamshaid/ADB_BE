package com.infotech.adb.service;

import com.infotech.adb.model.entity.User;
import com.infotech.adb.model.repository.UserRepository;
import com.infotech.adb.model.repository.UserRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), getGrantedAuthorities(username));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(String username) {
        return userRoleRepository.findRoleByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();

    }
}