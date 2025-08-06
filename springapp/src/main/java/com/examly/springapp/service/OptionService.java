package com.examly.springapp.service;

import com.examly.springapp.model.Option;
import com.examly.springapp.repository.OptionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public Option createOption(Option option) {
        return optionRepository.save(option);
    }

    public List<Option> getOptionsByPollId(Long pollId) {
        return optionRepository.findByPollId(pollId);
    }

    public void incrementVoteCount(Long optionId) {
        optionRepository.incrementVoteCount(optionId);
    }
}