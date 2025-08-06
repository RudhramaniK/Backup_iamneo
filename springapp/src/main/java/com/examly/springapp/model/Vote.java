package com.examly.springapp.model;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")  // Explicit table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "poll_id")  // Explicit foreign key column name
    private Poll poll;
    
    @ManyToOne
    @JoinColumn(name = "option_id")  // Explicit foreign key column name
    private Option option;
    
    @ManyToOne
    @JoinColumn(name = "voter_id")  // Explicit foreign key column name
    private User voter;
    
    private LocalDateTime timestamp;
}