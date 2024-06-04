package org.osariusz.lorepaint.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import org.osariusz.lorepaint.auth.LoginDto;
import org.osariusz.lorepaint.auth.RegisterDto;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.systemUserRole.SystemUserRoleService;
import org.osariusz.lorepaint.utils.RoleNames;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Autowired
    private SessionRegistry sessionRegistry;

    public void validateUser(User user) {
        Validation.validate(user, validator);
    }

    public void saveUser(User user) {
        validateUser(user);
        userRepository.save(user);
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean userRemoved(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) {
            return false;
        }
        return user.getRemovedAt() != null;
    }

    public User getUser(String username) {
        return userRepository.findByUsernameAndRemovedAtIsNull(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByRemovedAtIsNull();
    }

    public void createUser(RegisterDto registerDto, PasswordEncoder passwordEncoder) {
        LocalDateTime registerTime = LocalDateTime.now();

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setCreated_at(registerTime);

        SystemUserRole role = systemUserRoleService.createSystemUserRole(user, RoleNames.SYSTEM_USER_ROLE_NAME);

        user.setSystemUserRoles(new ArrayList<>(List.of(role)));

        userRepository.save(user);
        systemUserRoleService.saveRole(role);
    }

    public void login(LoginDto loginDto, AuthenticationManager authenticationManager, HttpServletRequest request, HttpServletResponse response) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticationResponse =
                authenticationManager.authenticate(authenticationRequest);

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        //request.getSession();

        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
    }

    public void removeUser(String username) {
        User user = getUser(username);
        user.setRemovedAt(LocalDateTime.now());
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sessionRegistry.getAllSessions(u, true).forEach(SessionInformation::expireNow); //TODO: fix session logout
        saveUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getSystemUserRoles().stream().map(SystemUserRole::toAuthority).collect(Collectors.toList()));
    }

}
