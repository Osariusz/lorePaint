package org.osariusz.lorepaint.user;

import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/all")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_ADMIN_ROLE_NAME)")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/remove/{username}")
    @PreAuthorize("hasAuthority(@RoleNames.SYSTEM_ADMIN_ROLE_NAME)")
    public void removeUser(@PathVariable String username) {
        userService.removeUser(username);
    }

}