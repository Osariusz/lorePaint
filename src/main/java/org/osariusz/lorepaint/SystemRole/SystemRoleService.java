package org.osariusz.lorepaint.SystemRole;

import jakarta.validation.*;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.SystemRole.SystemRole;
import org.osariusz.lorepaint.SystemRole.SystemRoleRepository;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreCreateDTO;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleRepository;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleService;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.systemUserRole.SystemUserRole;
import org.osariusz.lorepaint.systemUserRole.SystemUserRoleRepository;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.utils.RoleNames;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
public class SystemRoleService {
    @Autowired
    private SystemRoleRepository systemRoleRepository;

    @Autowired
    private Validator validator;

    public void validateRole(SystemRole systemRole) {
        Validation.validate(systemRole, validator);
    }

    public SystemRole getRoleByName(String roleName) {
        return systemRoleRepository.findByRole(roleName);
    }

    public void saveRole(SystemRole systemRole) {
        validateRole(systemRole);
        systemRoleRepository.save(systemRole);
    }

}
