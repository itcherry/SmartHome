package com.smarthome.SmartHome.security;

import com.smarthome.SmartHome.error.AuthenticationError;
import com.smarthome.SmartHome.exception.ExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrLogin(s, s);
        if (user == null)
           throw ExceptionFactory.create(AuthenticationError.NO_SUCH_USER);


        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findOne(id);
        if (user == null)
            throw ExceptionFactory.create(AuthenticationError.NO_SUCH_USER);

        return UserPrincipal.create(user);
    }
}
