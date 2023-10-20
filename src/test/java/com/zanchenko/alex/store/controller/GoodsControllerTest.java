package com.zanchenko.alex.store.controller;

import com.zanchenko.alex.store.domain.Good;
import com.zanchenko.alex.store.dto.GoodDTO;
import com.zanchenko.alex.store.mapper.GoodsMapper;
import com.zanchenko.alex.store.service.GoodsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.zanchenko.alex.store.mapper.GoodsMapper.mapToGood;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoodsService goodsService;

    private List<GoodDTO> sampleGoods;

    @BeforeEach
    void setUp() {
        GoodDTO good1 = new GoodDTO();
        good1.setId(1L);
        good1.setName("Iphone 12");
        good1.setPrice(600.0);

        GoodDTO good2 = new GoodDTO();
        good2.setId(2L);
        good2.setName("Iphone 13");
        good2.setPrice(800.0);

        sampleGoods = Arrays.asList(good1, good2);
        goodsService.saveGood(good1);
        goodsService.saveGood(good2);
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_USER"})
    public void testGetGoods() throws Exception {
        // Mock the behavior of the goodsService to return sampleGoods

        Mockito.when(goodsService.findAllGoods()).thenReturn(sampleGoods.stream()
                .map(GoodsMapper::mapToGood)
                .collect(Collectors.toList())
        );

        // Perform the GET request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/store/goods"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.goods").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.goods[0].name").value("Iphone 12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goods[1].price").value(800.0));
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_MODERATOR"})
    public void testAddGoods() throws Exception {
        // Mock the behavior of the goodsService to return a saved Good
        Good savedGood = new Good();
        savedGood.setId(3L);
        savedGood.setName("Iphone 14");
        savedGood.setPrice(1000.0);
        Mockito.when(goodsService.saveGood(Mockito.any(GoodDTO.class))).thenReturn(savedGood);

        // Create a JSON request body
        String requestJson = "{\"name\": \"Iphone 14\", \"price\": 1000.0}";

        // Perform the POST request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/store/add-goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Iphone 14"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1000.0));
    }
}