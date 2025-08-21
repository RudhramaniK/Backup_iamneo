package com.examly.springapp.dto;



import java.util.List;

public class PollRequest {
    private String question;
    private List<String> options;
    private String privacy;

    // Constructors, getters, and setters
    public PollRequest() {}

    public PollRequest(String question, List<String> options, String privacy) {
        this.question = question;
        this.options = options;
        this.privacy = privacy;
    }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
    public String getPrivacy() { return privacy; }
    public void setPrivacy(String privacy) { this.privacy = privacy; }
}