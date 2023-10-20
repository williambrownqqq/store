package com.zanchenko.alex.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodDTO {
    Long id;
    String name;
    double price;
    int quantity;
}
