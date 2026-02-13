package com.sportclub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "members")
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true)
    private String email;
    
    private String phone;
    
    private LocalDate birthDate;
    
    private String address;
    
    @Builder.Default
    private LocalDate registrationDate = LocalDate.now();
    
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, INACTIVE, PENDING
}