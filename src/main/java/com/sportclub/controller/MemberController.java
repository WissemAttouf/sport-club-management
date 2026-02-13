package com.sportclub.controller;

import com.sportclub.model.Member;
import com.sportclub.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@Tag(name = "Membres", description = "API de gestion des membres")
public class MemberController {
    
    @Autowired
    private MemberService memberService;
    
    @PostMapping
    @Operation(summary = "Créer un nouveau membre")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member newMember = memberService.createMember(member);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }
    
    @GetMapping
    @Operation(summary = "Lister tous les membres")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un membre par ID")
    public ResponseEntity<Member> getMemberById(
            @Parameter(description = "ID du membre", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un membre")
    public ResponseEntity<Member> updateMember(
            @PathVariable Long id,
            @RequestBody Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un membre")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    @Operation(summary = "Rechercher des membres par nom")
    public ResponseEntity<List<Member>> searchMembers(
            @Parameter(description = "Nom à rechercher", example = "Dupont")
            @RequestParam String name) {
        return ResponseEntity.ok(memberService.searchMembers(name));
    }
    
    @GetMapping("/count")
    @Operation(summary = "Compter le nombre de membres")
    public ResponseEntity<Long> countMembers() {
        return ResponseEntity.ok(memberService.countMembers());
    }
}