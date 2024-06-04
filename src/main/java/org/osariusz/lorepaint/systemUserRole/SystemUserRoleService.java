package org.osariusz.lorepaint.systemUserRole;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.SystemRole.SystemRoleService;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.utils.RoleNames;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SystemUserRoleService {
    @Autowired
    private SystemUserRoleRepository systemUserRoleRepository;

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    private Validator validator;

    public void validateRole(SystemUserRole systemUserRole) {
        Validation.validate(systemUserRole, validator);
    }

    public void saveRole(SystemUserRole systemUserRole) {
        validateRole(systemUserRole);
        systemUserRoleRepository.save(systemUserRole);
    }

    public SystemUserRole createSystemUserRole(User user, String roleName) {
        SystemUserRole userRole = new SystemUserRole();
        userRole.setUser(user);
        userRole.setRole(systemRoleService.getRoleByName(roleName));
        userRole.setGranted_at(LocalDateTime.now());
        return userRole;
    }
}
