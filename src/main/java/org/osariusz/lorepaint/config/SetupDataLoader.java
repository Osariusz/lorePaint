package org.osariusz.lorepaint.config;

import org.osariusz.lorepaint.SystemRole.SystemRole;
import org.osariusz.lorepaint.SystemRole.SystemRoleRepository;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreRole.LoreRoleRepository;
import org.osariusz.lorepaint.utils.RoleNames;
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
        if (!systemRoleRepository.existsByRole(RoleNames.SYSTEM_USER_ROLE_NAME)) {
            SystemRole user = new SystemRole();
            user.setRole(RoleNames.SYSTEM_USER_ROLE_NAME);
            systemRoleRepository.save(user);
        }

        if (!systemRoleRepository.existsByRole(RoleNames.SYSTEM_ADMIN_ROLE_NAME)) {
            SystemRole admin = new SystemRole();
            admin.setRole(RoleNames.SYSTEM_ADMIN_ROLE_NAME);
            systemRoleRepository.save(admin);
        }

    }

    public void createLoreRoles() {
        if (!loreRoleRepository.existsByRole(RoleNames.LORE_MEMBER_ROLE_NAME)) {
            LoreRole member = new LoreRole();
            member.setRole(RoleNames.LORE_MEMBER_ROLE_NAME);
            loreRoleRepository.save(member);
        }

        if (!loreRoleRepository.existsByRole(RoleNames.LORE_GM_ROLE_NAME)) {
            LoreRole gm = new LoreRole();
            gm.setRole(RoleNames.LORE_GM_ROLE_NAME);
            loreRoleRepository.save(gm);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
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
