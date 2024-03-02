package org.osariusz.lorepaint.role;

import org.osariusz.lorepaint.lore.LoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
}
