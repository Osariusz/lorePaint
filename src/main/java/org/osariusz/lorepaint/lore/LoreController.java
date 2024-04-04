package org.osariusz.lorepaint.lore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lore")
public class LoreController {
    @Autowired
    private LoreService loreService;

    @GetMapping("/{id}")
    Lore getLore(@PathVariable Long id) {
        Lore lore = loreService.getLoreById(id);
        return lore;
    }
}
