package org.osariusz.lorepaint.auth;

import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getSystemUserRoles().stream().map(SystemUserRole::toAuthority).collect(Collectors.toList()));
        return u;
    }


}
