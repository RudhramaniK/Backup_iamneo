package com.examly.springapp.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "options")  // Explicit table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String text;
    
    @Column(name = "vote_count")  // Explicit column name
    private int voteCount = 0;
    
    @ManyToOne
    @JoinColumn(name = "poll_id")  // Explicit foreign key column name
    private Poll poll;
}