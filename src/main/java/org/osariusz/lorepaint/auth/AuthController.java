package org.osariusz.lorepaint.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.osariusz.lorepaint.SystemRole.SystemRole;
import org.osariusz.lorepaint.SystemRole.SystemRoleRepository;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.systemUserRole.SystemUserRoleRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private CustomUserDetailsService userDetailsService;

    @Autowired
    private SystemUserRoleRepository systemUserRoleRepository;

    @Autowired
    private SystemRoleRepository systemRoleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private DelegatingSecurityContextRepository securityContextRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, SystemUserRoleRepository systemUserRoleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.systemUserRoleRepository = systemUserRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto,
                                        HttpServletRequest request,
                                        HttpServletResponse response)
    {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        request.getSession();

        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

        return ResponseEntity.ok().build();
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

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
