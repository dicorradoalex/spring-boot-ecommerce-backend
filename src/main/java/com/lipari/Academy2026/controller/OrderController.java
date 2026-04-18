package com.lipari.Academy2026.controller;

import com.lipari.Academy2026.dto.OrderRequestDTO;
import com.lipari.Academy2026.dto.OrderResponseDTO;
import com.lipari.Academy2026.entity.UserEntity;
import com.lipari.Academy2026.enums.OrderStatus;
import com.lipari.Academy2026.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO createdOrder = this.orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders() {
        // Recupero l'utente autenticato dal contesto di sicurezza di Spring
        // Il "Principal" contiene l'oggetto UserEntity che abbiamo inserito nel filtro JWT
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Recupero la lista degli ordini dal service usando l'ID dell'utente loggato
        List<OrderResponseDTO> myOrders = this.orderService.getOrdersByUser(currentUser.getId());

        // Restituisco la lista (anche se vuota []) con stato 200 OK
        return ResponseEntity.ok(myOrders);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable UUID id,
            @RequestParam OrderStatus newStatus) {

        OrderResponseDTO updatedOrder = this.orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@PathVariable UUID userId) {
        List<OrderResponseDTO> ordersList = this.orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(ordersList);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable UUID id) {
        OrderResponseDTO canceledOrder = this.orderService.cancelOrder(id);
        return ResponseEntity.ok(canceledOrder);
    }
}

