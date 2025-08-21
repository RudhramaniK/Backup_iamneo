package com.examly.springapp.dto;

import com.examly.springapp.model.Privacy;
import com.examly.springapp.model.Status;
import java.time.LocalDateTime;
import java.util.List;

public class PollResponseDTO {
    private Long id;
    private String question;
    private Privacy privacy;
    private Status status;
    private LocalDateTime createdAt;
    private Long creatorId;
    private String creatorName;
    private List<OptionDTO> options;
    // private boolean hasVoted; // Temporarily disabled to fix 500 errors

    // Default constructor
    public PollResponseDTO() {}

    // Full constructor
    public PollResponseDTO(Long id, String question, Privacy privacy, Status status, 
                         LocalDateTime createdAt, Long creatorId, String creatorName, 
                         List<OptionDTO> options) {
        this.id = id;
        this.question = question;
        this.privacy = privacy;
        this.status = status;
        this.createdAt = createdAt;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.options = options;
        // this.hasVoted = hasVoted; // Temporarily disabled
    }

    // Getters and setters
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

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }

    public String getCreatorName() { return creatorName; }
    public void setCreatorName(String creatorName) { this.creatorName = creatorName; }

    public List<OptionDTO> getOptions() { return options; }
    public void setOptions(List<OptionDTO> options) { this.options = options; }
    
    // public boolean getHasVoted() { return hasVoted; } // Temporarily disabled
    // public void setHasVoted(boolean hasVoted) { this.hasVoted = hasVoted; } // Temporarily disabled
}