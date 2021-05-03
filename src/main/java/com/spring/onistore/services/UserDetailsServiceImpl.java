package com.spring.onistore.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.spring.onistore.entity.User;
// import com.spring.onistore.repository.AppRoleRepository;
// import com.spring.onistore.repository.AppUserRepository;
import com.spring.onistore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        if (user.getIsAdmin()) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
            return new org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getEncryptedPassword(), roles);
        }

        roles = Arrays.asList(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getEncryptedPassword(),
                roles);
    }

}
