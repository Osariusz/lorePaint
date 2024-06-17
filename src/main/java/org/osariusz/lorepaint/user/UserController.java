package org.osariusz.lorepaint.user;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreService;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdate;
import org.osariusz.lorepaint.shared.UserRolesService;
import org.osariusz.lorepaint.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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