package org.osariusz.lorepaint.loreRole;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
