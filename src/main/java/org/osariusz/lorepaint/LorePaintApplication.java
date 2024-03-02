package org.osariusz.lorepaint;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LorePaintApplication implements CommandLineRunner {

    @Autowired
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(LorePaintApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Query query = entityManager.createNativeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
        List<String> tableNames = query.getResultList();

        for (String tableName : tableNames) {
            System.out.println(tableName);
        }
    }
}
