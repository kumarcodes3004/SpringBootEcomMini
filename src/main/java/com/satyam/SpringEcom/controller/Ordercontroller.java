package com.satyam.SpringEcom.controller;

import com.satyam.SpringEcom.model.dto.OrderRequest;
import com.satyam.SpringEcom.model.dto.OrderResponse;
import com.satyam.SpringEcom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Ordercontroller {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse =orderService.placeOrder(orderRequest);

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);

    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        List<OrderResponse> reponses=orderService.getAllOrderResponses();

        return new ResponseEntity<>(reponses,HttpStatus.OK);
    }
}
