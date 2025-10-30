package com.chessapi.controller;

import com.chessapi.dto.MatchDTO;
import com.chessapi.model.Match;
import com.chessapi.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService svc;

    public MatchController(MatchService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<Match> list(
            @RequestParam(required = false) String result,
            @RequestParam(required = false) String orderBy
    ) {
        return svc.listAll(result, orderBy);
    }

    @GetMapping("/{id}")
    public Match get(@PathVariable UUID id) {
        return svc.getById(id);
    }

    @PostMapping
    public Match create(@RequestBody MatchDTO dto) {
        return svc.create(dto);
    }

    @PutMapping("/{id}")
    public Match update(@PathVariable UUID id, @RequestBody MatchDTO dto) {
        return svc.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        svc.delete(id);
    }
}
