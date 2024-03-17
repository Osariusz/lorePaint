package org.osariusz.lorepaint.user;

import jakarta.validation.Validator;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    public void validateUser(User user) {
        Validation.validate(user, validator);
    }

    public void saveUser(User user) {
        validateUser(user);
        userRepository.save(user);
    }

}
