package org.osariusz.lorepaint.lore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoreController {
    @Autowired
    private LoreService loreService;

    @GetMapping("/lore/{id}")
    Lore getLore(@PathVariable Long id) {
        return loreService.getLoreById(id);
    }
}
