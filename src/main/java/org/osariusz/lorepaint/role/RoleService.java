package org.osariusz.lorepaint.role;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Validator validator;

    public void validateRole(Role role) {
        Validation.validate(role, validator);
    }

    public void saveRole(Role role) {
        validateRole(role);
        roleRepository.save(role);
    }
}
