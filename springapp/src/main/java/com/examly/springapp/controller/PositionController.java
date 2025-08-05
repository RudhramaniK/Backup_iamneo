package com.examly.springapp.controller;

import com.examly.springapp.model.Position;
import com.examly.springapp.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@CrossOrigin(origins = "https://8081-localhost", allowCredentials = "true")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @PostMapping
    public Position createPosition(@RequestBody Position position) {
        return positionService.addPosition(position);
    }

    @GetMapping("/{id}")
    public Position getPosition(@PathVariable Long id) {
        return positionService.getPositionById(id)
                .orElseThrow(() -> new RuntimeException("Position not found"));
    }

    @PutMapping("/{id}")
    public Position updatePosition(@PathVariable Long id, @RequestBody Position position) {
        return positionService.updatePosition(id, position);
    }

    @DeleteMapping("/{id}")
    public void deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
    }
}
