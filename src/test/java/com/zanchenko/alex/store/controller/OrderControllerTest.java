package com.zanchenko.alex.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zanchenko.alex.store.domain.Order;
import com.zanchenko.alex.store.domain.OrderItem;
import com.zanchenko.alex.store.dto.GoodDTO;
import com.zanchenko.alex.store.dto.OrderDTO;
import com.zanchenko.alex.store.service.GoodsService;
import com.zanchenko.alex.store.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.zanchenko.alex.store.mapper.GoodsMapper.mapToGood;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private GoodsService goodsService;

    private List<GoodDTO> sampleGoods;

    private List<OrderItem> orderItems;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        orderItems = new ArrayList<>();

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

        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();
        orderItem1.setGoods(mapToGood(good1));
        orderItem2.setGoods(mapToGood(good2));
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_USER"})
    void testCreateOrder() throws Exception {
        // Create a sample order DTO
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderItems(orderItems);
        // Mock the behavior of the orderService to return a created order
        Order createdOrder = new Order();
        createdOrder.setId(1L);
        createdOrder.setOrderItems(orderItems);
        when(orderService.createOrder(orderDTO)).thenReturn(createdOrder);

        // Perform the POST request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/new")
                        .content(objectMapper.writeValueAsString(orderDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_USER"})
    public void testGetAllOrders() throws Exception {
        // Create a sample list of orders that should be returned by the service
        Order order1 = new Order();
        order1.setOrderItems(orderItems);
        order1.setOrderTime(LocalDateTime.now());
        order1.setPaid(false);
        order1.setId(1L);

        Order order2 = new Order();
        order2.setOrderItems(orderItems);
        order2.setOrderTime(LocalDateTime.now());
        order2.setPaid(false);
        order2.setId(2L);

        // Mock the service to return a list of orders
        when(orderService.getAllOrders())
                .thenReturn(Arrays.asList(order1, order2));

        // Perform a GET request to retrieve all orders
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_USER"})
    public void testGetOrderById() throws Exception {
        // Create a sample Order that should be returned by the service
        Order order = new Order();
        order.setOrderItems(orderItems);
        order.setOrderTime(LocalDateTime.now());
        order.setPaid(false);
        order.setId(1L);

        // Mock the service to return the order
        when(orderService.getOrderById(Mockito.anyLong()))
                .thenReturn(order);

        // Perform a GET request to retrieve an order by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{orderId}", 1L))
                .andExpect(status().isOk());
    }
}