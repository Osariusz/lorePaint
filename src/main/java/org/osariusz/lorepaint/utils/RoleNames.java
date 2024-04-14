package org.osariusz.lorepaint.utils;

import org.springframework.stereotype.Component;

@Component("RoleNames")
public final class RoleNames {
    //system
    public static final String SYSTEM_USER_ROLE_NAME = "USER";
    public static final String SYSTEM_ADMIN_ROLE_NAME = "ADMIN";

    //lore
    public static final String LORE_GM_ROLE_NAME = "GM";
    public static final String LORE_MEMBER_ROLE_NAME = "MEMBER";

}
