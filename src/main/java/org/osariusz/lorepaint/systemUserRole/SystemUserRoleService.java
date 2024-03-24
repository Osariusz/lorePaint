package org.osariusz.lorepaint.systemUserRole;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemUserRoleService {
    @Autowired
    private SystemUserRoleRepository systemUserRoleRepository;

    @Autowired
    private Validator validator;

    public void validateRole(SystemUserRole systemUserRole) {
        Validation.validate(systemUserRole, validator);
    }

    public void saveRole(SystemUserRole systemUserRole) {
        validateRole(systemUserRole);
        systemUserRoleRepository.save(systemUserRole);
    }
}
