package com.examly.springapp.service;

import com.examly.springapp.model.Position;
import com.examly.springapp.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public Position addPosition(Position position) {
        return positionRepository.save(position);
    }

    public Optional<Position> getPositionById(Long id) {
        return positionRepository.findById(id);
    }

    public void deletePosition(Long id) {
        positionRepository.deleteById(id);
    }

    public Position updatePosition(Long id, Position updatedPosition) {
        return positionRepository.findById(id)
                .map(position -> {
                    position.setName(updatedPosition.getName());
                    return positionRepository.save(position);
                })
                .orElseThrow(() -> new RuntimeException("Position not found with id: " + id));
    }
}
