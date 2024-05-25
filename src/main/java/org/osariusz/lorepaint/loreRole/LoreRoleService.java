package org.osariusz.lorepaint.loreRole;

import jakarta.validation.*;
import org.modelmapper.ModelMapper;
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
public class LoreRoleService {
    @Autowired
    private LoreRoleRepository loreRoleRepository;

    @Autowired
    private Validator validator;

    public void validateRole(LoreRole loreRole) {
        Validation.validate(loreRole, validator);
    }

    public LoreRole getRoleByName(String roleName) {
        return loreRoleRepository.findByRole(roleName);
    }

    public void saveRole(LoreRole loreRole) {
        validateRole(loreRole);
        loreRoleRepository.save(loreRole);
    }
}
