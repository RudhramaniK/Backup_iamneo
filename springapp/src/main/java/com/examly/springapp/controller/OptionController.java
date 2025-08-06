package com.examly.springapp.controller;

import com.examly.springapp.model.Option;
import com.examly.springapp.service.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<Option> createOption(@RequestBody Option option) {
        return ResponseEntity.ok(optionService.createOption(option));
    }

    @GetMapping("/poll/{pollId}")
    public ResponseEntity<List<Option>> getOptionsByPollId(@PathVariable Long pollId) {
        return ResponseEntity.ok(optionService.getOptionsByPollId(pollId));
    }
}