package com.sportclub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportclub.model.Member;
import com.sportclub.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@DisplayName("Tests du contrôleur MemberController")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
    @DisplayName("POST /api/members - Créer un membre")
    void createMember_Success() throws Exception {
        // Arrange
        when(memberService.createMember(any(Member.class))).thenReturn(member);

        // Act & Assert
        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@email.com"));
    }

    @Test
    @DisplayName("GET /api/members - Lister tous les membres")
    void getAllMembers_Success() throws Exception {
        // Arrange
        List<Member> members = Arrays.asList(member, member2);
        when(memberService.getAllMembers()).thenReturn(members);

        // Act & Assert
        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    @DisplayName("GET /api/members/{id} - Récupérer un membre par ID")
    void getMemberById_Success() throws Exception {
        // Arrange
        when(memberService.getMemberById(1L)).thenReturn(member);

        // Act & Assert
        mockMvc.perform(get("/api/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("PUT /api/members/{id} - Mettre à jour un membre")
    void updateMember_Success() throws Exception {
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
        
        when(memberService.updateMember(eq(1L), any(Member.class))).thenReturn(updatedMember);

        // Act & Assert
        mockMvc.perform(put("/api/members/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMember)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@email.com"));
    }

    @Test
    @DisplayName("DELETE /api/members/{id} - Supprimer un membre")
    void deleteMember_Success() throws Exception {
        // Arrange
        when(memberService.getMemberById(1L)).thenReturn(member);

        // Act & Assert
        mockMvc.perform(delete("/api/members/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/members/search?name= - Rechercher des membres")
    void searchMembers_Success() throws Exception {
        // Arrange
        List<Member> members = Arrays.asList(member);
        when(memberService.searchMembers("Doe")).thenReturn(members);

        // Act & Assert
        mockMvc.perform(get("/api/members/search")
                .param("name", "Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    @DisplayName("GET /api/members/count - Compter les membres")
    void countMembers_Success() throws Exception {
        // Arrange
        when(memberService.countMembers()).thenReturn(10L);

        // Act & Assert
        mockMvc.perform(get("/api/members/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
}