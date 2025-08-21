package com.examly.springapp.model;



import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String text;

    private int voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    // Constructors, getters, and setters
    public Option() {}

    public Option(String text, Poll poll) {
        this.text = text;
        this.poll = poll;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}