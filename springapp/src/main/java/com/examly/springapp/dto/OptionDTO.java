package com.examly.springapp.dto;

public class OptionDTO {
    private Long id;
    private String text;
    private int voteCount;

    // Default constructor
    public OptionDTO() {}

    // Full constructor
    public OptionDTO(Long id, String text, int voteCount) {
        this.id = id;
        this.text = text;
        this.voteCount = voteCount;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
}