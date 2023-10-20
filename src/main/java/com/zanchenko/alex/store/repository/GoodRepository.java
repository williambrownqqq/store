package com.zanchenko.alex.store.repository;

import com.zanchenko.alex.store.domain.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {

}
