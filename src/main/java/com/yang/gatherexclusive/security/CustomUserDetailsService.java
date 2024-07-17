package com.yang.gatherexclusive.security;

import com.yang.gatherexclusive.entity.User;
import com.yang.gatherexclusive.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), mapRolesToAuthorities(Collections.singletonList((Object) "USER")));
        }

    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Object> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("USER"))
                .collect(Collectors.toList());
    }
}
