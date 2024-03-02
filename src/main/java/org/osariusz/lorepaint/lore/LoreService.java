package org.osariusz.lorepaint.lore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoreService {
    @Autowired
    private LoreRepository loreRepository;

    public Lore getLoreById(Long id) {
        Lore lore = loreRepository.findById(id).orElse(null);
        if(lore == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lore not found");
        }
        return lore;
    }

    public void saveLore(Lore lore) {

    }
}
