package com.smarthome.smarthome.security;

import com.smarthome.smarthome.error.AuthenticationError;
import com.smarthome.smarthome.exception.ExceptionFactory;
import com.smarthome.smarthome.repository.UserRepository;
import com.smarthome.smarthome.repository.entity.User;
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
