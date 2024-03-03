package org.osariusz.lorepaint.lore;

import jakarta.validation.*;
import org.osariusz.lorepaint.utils.Validation;
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
        Validation.validate(lore, validator);
    }

    public void saveLore(Lore lore) {
        validateLore(lore);
        loreRepository.save(lore);
    }
}
