package org.osariusz.lorepaint.loreUserRole;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoreUserRoleService {
    @Autowired
    private LoreUserRoleRepository loreUserRoleRepository;

    @Autowired
    private Validator validator;

    public void validateRole(LoreUserRole loreUserRole) {
        Validation.validate(loreUserRole, validator);
    }

    public void saveRole(LoreUserRole loreUserRole) {
        validateRole(loreUserRole);
        loreUserRoleRepository.save(loreUserRole);
    }

}
