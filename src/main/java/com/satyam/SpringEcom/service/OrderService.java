package com.satyam.SpringEcom.service;

import com.satyam.SpringEcom.model.Order;
import com.satyam.SpringEcom.model.OrderItem;
import com.satyam.SpringEcom.model.Product;
import com.satyam.SpringEcom.model.dto.OrderItemRequest;
import com.satyam.SpringEcom.model.dto.OrderItemResponse;
import com.satyam.SpringEcom.model.dto.OrderRequest;
import com.satyam.SpringEcom.model.dto.OrderResponse;
import com.satyam.SpringEcom.repository.OrderRepo;
import com.satyam.SpringEcom.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

        @Autowired
        private OrderRepo orderRepo;

        @Autowired
        private ProductRepo productRepo;

    public OrderResponse placeOrder(OrderRequest request) {
            Order order = new Order();
            String orderId ="ORD"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
            order.setOrderId(orderId);
            order.setCustomerName(request.customerName());
            order.setEmail(request.email());
            order.setStatus("PLACED");
            order.setOrderDate(LocalDate.now());
            List<OrderItem> orderItems= new ArrayList<>();

            for(OrderItemRequest itemReq: request.items()){
                Product product =productRepo.findById(itemReq.productId())
                        .orElseThrow(()-> new RuntimeException("Product not found!"));
                product.setStockQuantity(product.getStockQuantity()- itemReq.quantity());
                productRepo.save(product);

                OrderItem orderItem =OrderItem.builder()
                        .product(product)
                        .quantity(itemReq.quantity())
                        .totalPrice(itemReq.quantity()*product.getPrice())
                        .order(order)
                        .build();

                orderItems.add(orderItem);

            }

            order.setOrderItems(orderItems);
            Order savedOrder =orderRepo.save(order);

            List<OrderItemResponse> itemResponses  = new ArrayList<>();

            for(OrderItem item : order.getOrderItems()){
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                itemResponses.add(orderItemResponse);
            }

            OrderResponse orderResponse= new OrderResponse(
                    savedOrder.getOrderId(),
                    savedOrder.getCustomerName(),
                    savedOrder.getEmail(),
                    savedOrder.getStatus(),
                    savedOrder.getOrderDate(),
                    itemResponses
            );
                return orderResponse;

    }

    public List<OrderResponse> getAllOrderResponses() {
        List<Order>  orders=orderRepo.findAll();
        List<OrderResponse> responses =new ArrayList<>();

        for(Order order :orders){

            List<OrderItem> orderItems =order.getOrderItems();
            List<OrderItemResponse> orderItemResponses=new ArrayList<>();
            for(OrderItem item:orderItems){
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                orderItemResponses.add(orderItemResponse);
            }
            OrderResponse orderResponse =new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    orderItemResponses
            );
            responses.add(orderResponse);
        }
        return responses;
    }
}
