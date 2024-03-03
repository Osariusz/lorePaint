package org.osariusz.lorepaint.user;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.role.Role;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    public void validateUser(User user) {
        Validation.validate(user, validator);
    }

}
