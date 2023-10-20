package com.zanchenko.alex.store.service.impl;

import com.zanchenko.alex.store.domain.Good;
import com.zanchenko.alex.store.dto.GoodDTO;
import com.zanchenko.alex.store.repository.GoodRepository;
import com.zanchenko.alex.store.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zanchenko.alex.store.mapper.GoodsMapper.mapToGood;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodRepository goodRepository;

    @Override
    public List<Good> findAllGoods() {
        return goodRepository.findAll();
    }

    @Override
    public Good saveGood(GoodDTO goodDTO) {
        return mapToGood(goodDTO);
    }
}
