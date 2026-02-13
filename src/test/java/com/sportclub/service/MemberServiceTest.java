package com.sportclub.service;

import com.sportclub.model.Member;
import com.sportclub.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires du service MemberService")
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;
    private Member member2;

   @BeforeEach
void setUp() {
    member = new Member(
        1L, 
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
        2L, 
        "Jane", 
        "Smith", 
        "jane.smith@email.com",
        "0987654321",
        LocalDate.of(1992, 2, 2),
        "456 Avenue, Town",
        LocalDate.now(),
        "ACTIVE"
    );
}

    @Test
    @DisplayName("Devrait créer un membre avec succès")
    void createMember_Success() {
        // Arrange
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // Act
        Member result = memberService.createMember(member);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("john.doe@email.com");
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    @DisplayName("Devrait échouer si l'email existe déjà")
    void createMember_EmailAlreadyExists() {
        // Arrange
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));

        // Act & Assert
        assertThatThrownBy(() -> memberService.createMember(member))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Email déjà utilisé");
        
        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("Devrait retourner tous les membres")
    void getAllMembers_Success() {
        // Arrange
        List<Member> members = Arrays.asList(member, member2);
        when(memberRepository.findAll()).thenReturn(members);

        // Act
        List<Member> result = memberService.getAllMembers();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(1).getFirstName()).isEqualTo("Jane");
    }

    @Test
    @DisplayName("Devrait trouver un membre par ID")
    void getMemberById_Found() {
        // Arrange
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // Act
        Member result = memberService.getMemberById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getEmail()).isEqualTo("john.doe@email.com");
    }

    @Test
    @DisplayName("Devrait lancer une exception si membre non trouvé par ID")
    void getMemberById_NotFound() {
        // Arrange
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> memberService.getMemberById(99L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Membre non trouvé");
    }

    @Test
    @DisplayName("Devrait mettre à jour un membre")
    void updateMember_Success() {
        // Arrange
        Member updatedMember = new Member(
            1L, 
            "John", 
            "Updated", 
            "john.updated@email.com",
            "1111111111",
            LocalDate.of(1990, 1, 1),
            "New Address",
            LocalDate.now(),
            "INACTIVE"
        );

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        // Act
        Member result = memberService.updateMember(1L, updatedMember);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLastName()).isEqualTo("Updated");
        assertThat(result.getEmail()).isEqualTo("john.updated@email.com");
        assertThat(result.getStatus()).isEqualTo("INACTIVE");
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("Devrait supprimer un membre")
    void deleteMember_Success() {
        // Arrange
        when(memberRepository.existsById(1L)).thenReturn(true);
        doNothing().when(memberRepository).deleteById(1L);

        // Act
        memberService.deleteMember(1L);

        // Assert
        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Devrait lancer une exception si suppression d'un membre inexistant")
    void deleteMember_NotFound() {
        // Arrange
        when(memberRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> memberService.deleteMember(99L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Membre non trouvé");
        
        verify(memberRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Devrait rechercher des membres par nom")
    void searchMembers_Success() {
        // Arrange
        List<Member> members = Arrays.asList(member);
        when(memberRepository.findByLastNameContainingIgnoreCase("Doe")).thenReturn(members);

        // Act
        List<Member> result = memberService.searchMembers("Doe");

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLastName()).isEqualTo("Doe");
    }

    @Test
    @DisplayName("Devrait compter les membres")
    void countMembers_Success() {
        // Arrange
        when(memberRepository.count()).thenReturn(5L);

        // Act
        long count = memberService.countMembers();

        // Assert
        assertThat(count).isEqualTo(5);
    }
}