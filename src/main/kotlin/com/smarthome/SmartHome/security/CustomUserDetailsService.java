package com.smarthome.SmartHome.security;

import com.smarthome.SmartHome.entity.User;
import com.smarthome.SmartHome.error.AuthenticationError;
import com.smarthome.SmartHome.exception.ExceptionFactory;
import com.smarthome.SmartHome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(s);
        if (user == null)
            throw ExceptionFactory.create(AuthenticationError.NO_SUCH_USER);


        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw ExceptionFactory.create(AuthenticationError.NO_SUCH_USER);
        } else {
            return UserPrincipal.create(userOptional.get());
        }
    }
}
