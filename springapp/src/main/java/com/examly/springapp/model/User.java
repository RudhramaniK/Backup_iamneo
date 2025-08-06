package com.examly.springapp.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")  // Explicit table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column(unique = true)
    private String email;
    
    @Column(name = "password_hash")  // Explicit column name
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, CREATOR, VOTER
}