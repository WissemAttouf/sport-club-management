package com.sportclub.repository;

import com.sportclub.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests du repository Member")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        // Création des membres avec le constructeur
        member1 = new Member(
            null, // ID sera généré automatiquement
            "John",
            "Doe",
            "john.doe@email.com",
            "0123456789",
            LocalDate.of(1990, 1, 1),
            "123 Street, City",
            LocalDate.now(),
            "ACTIVE"
        );

        member2 = new Member(
            null, // ID sera généré automatiquement
            "Jane",
            "Smith",
            "jane.smith@email.com",
            "0987654321",
            LocalDate.of(1992, 2, 2),
            "456 Avenue, Town",
            LocalDate.now(),
            "ACTIVE"
        );

        // Sauvegarde
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
    }

    @Test
    @DisplayName("Devrait sauvegarder et retrouver un membre")
    void saveAndFind() {
        // Act
        Optional<Member> found = memberRepository.findById(member1.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("John");
        assertThat(found.get().getEmail()).isEqualTo("john.doe@email.com");
    }

    @Test
    @DisplayName("Devrait trouver un membre par email")
    void findByEmail_Success() {
        // Act
        Optional<Member> found = memberRepository.findByEmail("john.doe@email.com");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    @DisplayName("Devrait retourner empty si email non trouvé")
    void findByEmail_NotFound() {
        // Act
        Optional<Member> found = memberRepository.findByEmail("unknown@email.com");

        // Assert
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Devrait rechercher des membres par nom (insensible à la casse)")
    void findByLastNameContainingIgnoreCase_Success() {
        // Act
        List<Member> results = memberRepository.findByLastNameContainingIgnoreCase("doe");

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    @DisplayName("Devrait retourner tous les membres")
    void findAll_Success() {
        // Act
        List<Member> allMembers = memberRepository.findAll();

        // Assert
        assertThat(allMembers).hasSize(2);
        assertThat(allMembers)
                .extracting(Member::getFirstName)
                .containsExactlyInAnyOrder("John", "Jane");
    }

    @Test
    @DisplayName("Devrait mettre à jour un membre")
    void updateMember() {
        // Arrange
        Member member = memberRepository.findById(member1.getId()).get();
        member.setFirstName("Jonathan");
        member.setPhone("9999999999");

        // Act
        memberRepository.save(member);
        Optional<Member> updated = memberRepository.findById(member1.getId());

        // Assert
        assertThat(updated).isPresent();
        assertThat(updated.get().getFirstName()).isEqualTo("Jonathan");
        assertThat(updated.get().getPhone()).isEqualTo("9999999999");
    }

    @Test
    @DisplayName("Devrait supprimer un membre")
    void deleteMember() {
        // Act
        memberRepository.deleteById(member1.getId());
        Optional<Member> found = memberRepository.findById(member1.getId());

        // Assert
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Devrait vérifier l'existence d'un membre par ID")
    void existsById() {
        // Act & Assert
        assertThat(memberRepository.existsById(member1.getId())).isTrue();
        assertThat(memberRepository.existsById(999L)).isFalse();
    }
}