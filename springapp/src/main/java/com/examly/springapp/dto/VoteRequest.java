package com.examly.springapp.dto;


public class VoteRequest {
    private Long optionId;

    // Constructors, getters, and setters
    public VoteRequest() {}

    public VoteRequest(Long optionId) {
        this.optionId = optionId;
    }

    public Long getOptionId() { return optionId; }
    public void setOptionId(Long optionId) { this.optionId = optionId; }
}