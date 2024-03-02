package org.osariusz.lorepaint;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LorePaintApplication {

    public static void main(String[] args) {
        SpringApplication.run(LorePaintApplication.class, args);
    }
}
