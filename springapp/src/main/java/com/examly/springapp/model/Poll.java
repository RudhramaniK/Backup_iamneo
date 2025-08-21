package com.examly.springapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "polls")
public class Poll { // Make sure class is public
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String question;

    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Option> options;

    // Public constructor
    public Poll() {}

    public Poll(String question, Privacy privacy, Status status, User creator) {
        this.question = question;
        this.privacy = privacy;
        this.status = status;
        this.creator = creator;
        this.createdAt = LocalDateTime.now();
    }

    // Public getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public Privacy getPrivacy() { return privacy; }
    public void setPrivacy(Privacy privacy) { this.privacy = privacy; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public User getCreator() { return creator; }
    public void setCreator(User creator) { this.creator = creator; }
    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }
}