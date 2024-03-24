package org.osariusz.lorepaint.config;

import org.osariusz.lorepaint.SystemRole.SystemRole;
import org.osariusz.lorepaint.SystemRole.SystemRoleRepository;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private SystemRoleRepository systemRoleRepository;

    @Autowired
    private LoreRoleRepository loreRoleRepository;

    public void createSystemRoles() {
        if(!systemRoleRepository.existsByRole(SystemRole.UserRole.USER)) {
            SystemRole user = new SystemRole();
            user.setRole(SystemRole.UserRole.USER);
            systemRoleRepository.save(user);
        }

        if(!systemRoleRepository.existsByRole(SystemRole.UserRole.ADMIN)) {
            SystemRole admin = new SystemRole();
            admin.setRole(SystemRole.UserRole.ADMIN);
            systemRoleRepository.save(admin);
        }

    }

    public void createLoreRoles() {
        if(!loreRoleRepository.existsByRole(LoreRole.UserRole.MEMBER)) {
            LoreRole member = new LoreRole();
            member.setRole(LoreRole.UserRole.MEMBER);
            loreRoleRepository.save(member);
        }

        if(!loreRoleRepository.existsByRole(LoreRole.UserRole.GM)) {
            LoreRole gm = new LoreRole();
            gm.setRole(LoreRole.UserRole.GM);
            loreRoleRepository.save(gm);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup) {
            return;
        }
        createSystemRoles();
        createLoreRoles();

    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
