package com.zanchenko.alex.store.controller;

import com.zanchenko.alex.store.domain.Good;
import com.zanchenko.alex.store.dto.GoodDTO;
import com.zanchenko.alex.store.mapper.GoodsMapper;
import com.zanchenko.alex.store.service.GoodsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zanchenko.alex.store.mapper.GoodsMapper.mapToGoodDTO;

@RestController
@CrossOrigin
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/goods") // get all goods as a user or as a moderator
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<Map<String, List<GoodDTO>>> getGoods() {
        List<GoodDTO> goods = goodsService.findAllGoods().stream()
                .map(GoodsMapper::mapToGoodDTO)
                .collect(Collectors.toList());
        Map<String, List<GoodDTO>> response = new HashMap<>();
        response.put("goods", goods);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-goods") // moderator add goods to db
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> addGoods(@Valid @RequestBody GoodDTO goodDTO,
                           BindingResult result) {
        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for(FieldError error: result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Good savedGood = goodsService.saveGood(goodDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGood);
    }
}
