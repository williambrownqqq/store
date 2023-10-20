package com.zanchenko.alex.store.mapper;

import com.zanchenko.alex.store.domain.Good;
import com.zanchenko.alex.store.dto.GoodDTO;

public class GoodsMapper {

    public static GoodDTO mapToGoodDTO(Good good){
        return GoodDTO.builder()
                .id(good.getId())
                .name(good.getName())
                .price(good.getPrice())
                .quantity(good.getQuantity())
                .build();
    }

    public static Good mapToGood(GoodDTO goodDTO){
        return Good.builder()
                .id(goodDTO.getId())
                .name(goodDTO.getName())
                .price(goodDTO.getPrice())
                .quantity(goodDTO.getQuantity())
                .build();
    }
}
