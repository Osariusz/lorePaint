package org.osariusz.lorepaint;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.osariusz.lorepaint.lore.Lore;
import org.osariusz.lorepaint.lore.LoreRepository;
import org.osariusz.lorepaint.lore.LoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class LorePaintApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(LorePaintApplication.class, args);
        LoreService service = applicationContext.getBean(LoreService.class);
        Lore lore = new Lore();
        service.validateLore(lore);
    }
}
