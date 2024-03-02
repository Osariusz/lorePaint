package org.osariusz.lorepaint.lore;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class LoreService {
    @Autowired
    private LoreRepository loreRepository;

    @Autowired
    private Validator validator;

    public Lore getLoreById(Long id) {
        Lore lore = loreRepository.findById(id).orElse(null);
        if(lore == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lore not found");
        }
        return lore;
    }

    public void validateLore(Lore lore) {
        Set<ConstraintViolation<Lore>> violations = validator.validate(lore);

        if(!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for(ConstraintViolation<Lore> violation : violations) {
                builder.append(violation.getMessage());
                builder.append("\n");
            }
            throw new ConstraintViolationException("Violated lore constraints: " + builder.toString(), violations);
        }
    }

    public void saveLore(Lore lore) {
        validateLore(lore);
        loreRepository.save(lore);
    }
}
