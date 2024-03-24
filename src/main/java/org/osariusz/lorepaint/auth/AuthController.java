package org.osariusz.lorepaint.auth;

import org.osariusz.lorepaint.SystemRole.SystemRole;
import org.osariusz.lorepaint.SystemRole.SystemRoleRepository;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.systemUserRole.SystemUserRoleRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    @Autowired
    private SystemUserRoleRepository systemUserRoleRepository;

    @Autowired
    private SystemRoleRepository systemRoleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, SystemUserRoleRepository systemUserRoleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.systemUserRoleRepository = systemUserRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        LocalDateTime registerTime = LocalDateTime.now();

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setCreated_at(registerTime);

        SystemUserRole userRole = new SystemUserRole();
        userRole.setUser(user);
        userRole.setRole(systemRoleRepository.findByRole(SystemRole.UserRole.USER));
        userRole.setGranted_at(registerTime);

        user.setSystemUserRoles(new ArrayList<>(List.of(userRole)));

        userRepository.save(user);
        systemUserRoleRepository.save(userRole);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }


}
