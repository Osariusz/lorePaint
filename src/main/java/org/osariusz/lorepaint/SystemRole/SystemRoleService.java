package org.osariusz.lorepaint.SystemRole;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
