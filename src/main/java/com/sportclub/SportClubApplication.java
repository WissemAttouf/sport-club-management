package com.sportclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SportClubApplication {
    public static void main(String[] args) {
        SpringApplication.run(SportClubApplication.class, args);
        System.out.println("✅ Sport Club Management démarré !");
        System.out.println("📱 API: http://localhost:8080/api/members");
        System.out.println("📚 H2 Console: http://localhost:8080/h2-console");
    }
}