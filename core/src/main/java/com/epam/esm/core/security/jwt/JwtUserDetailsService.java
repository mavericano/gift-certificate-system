package com.epam.esm.core.security.jwt;

import com.epam.esm.core.entity.User;
import com.epam.esm.core.security.jwt.JwtUser;
import com.epam.esm.core.security.jwt.JwtUserFactory;
import com.epam.esm.core.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("")); //being caught higher, unsuccessfulAuthentication  method called
        return JwtUserFactory.create(user);
    }
}
