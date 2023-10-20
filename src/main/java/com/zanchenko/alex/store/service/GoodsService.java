package com.zanchenko.alex.store.service;

import com.zanchenko.alex.store.domain.Good;
import com.zanchenko.alex.store.dto.GoodDTO;

import java.util.List;

public interface GoodsService {

    List<Good> findAllGoods();

    Good saveGood(GoodDTO goodDTO);


}
