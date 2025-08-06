package com.examly.springapp.model;

import javax.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "polls")  // Explicit table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "creator_id")  // Explicit foreign key column name
    private User creator;
    
    private String question;
    
    @Enumerated(EnumType.STRING)
    private Privacy privacy; // PUBLIC, PRIVATE
    
    @Enumerated(EnumType.STRING)
    private Status status; // OPEN, CLOSED
    
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Option> options;
}