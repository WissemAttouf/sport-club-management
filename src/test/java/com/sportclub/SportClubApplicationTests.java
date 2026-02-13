package com.sportclub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SportClubApplicationTests {

    @Test
    void contextLoads() {
        // Test de chargement du contexte Spring
        System.out.println("✅ Contexte Spring chargé avec succès");
    }
}