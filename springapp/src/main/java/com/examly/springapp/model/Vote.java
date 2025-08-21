package com.examly.springapp.model;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"poll_id", "voter_id"})
})
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    @JsonIgnore
    private Poll poll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    @JsonIgnore
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id")
    @JsonIgnore
    private User voter;

    private LocalDateTime timestamp;

    // Constructors, getters, and setters
    public Vote() {}

    public Vote(Poll poll, Option option, User voter) {
        this.poll = poll;
        this.option = option;
        this.voter = voter;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    @JsonIgnore
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
    @JsonIgnore
    public Option getOption() { return option; }
    public void setOption(Option option) { this.option = option; }
    @JsonIgnore
    public User getVoter() { return voter; }
    public void setVoter(User voter) { this.voter = voter; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
