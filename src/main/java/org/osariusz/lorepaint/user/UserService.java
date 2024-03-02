package org.osariusz.lorepaint.user;

import org.osariusz.lorepaint.lore.LoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
