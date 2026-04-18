package com.lipari.Academy2026.service.impl;

import com.lipari.Academy2026.dto.OrderResponseDTO;
import com.lipari.Academy2026.dto.OrderEntryRequestDTO;
import com.lipari.Academy2026.dto.OrderRequestDTO;
import com.lipari.Academy2026.entity.OrderEntity;
import com.lipari.Academy2026.entity.OrderEntryEntity;
import com.lipari.Academy2026.entity.ProductEntity;
import com.lipari.Academy2026.entity.UserEntity;
import com.lipari.Academy2026.enums.OrderStatus;
import com.lipari.Academy2026.exception.InvalidOrderStateException;
import com.lipari.Academy2026.exception.OutOfStockException;
import com.lipari.Academy2026.exception.ResourceNotFoundException;
import com.lipari.Academy2026.mapper.OrderMapper;
import com.lipari.Academy2026.repository.OrderRepository;
import com.lipari.Academy2026.repository.ProductRepository;
import com.lipari.Academy2026.repository.UserRepository;
import com.lipari.Academy2026.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    // Dipendenze
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDto) {

        // Recupero utente loggato
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Inizializza l'ordine
        OrderEntity newOrder = OrderEntity.builder()
                .user(currentUser) // associa all'utente loggato
                .status(OrderStatus.CREATED)
                .orderTime(LocalDateTime.now())
                .total(BigDecimal.ZERO)
                .entries(new ArrayList<>())
                .build();

        // Elaborazione delle righe dell'ordine (Entries)
        for (OrderEntryRequestDTO entryDto : orderRequestDto.entries()) {

            // Recupero prodotto dal database
            Optional<ProductEntity> productOpt = this.productRepository.findById(entryDto.productId());

            // Controllo esistenza
            if (!productOpt.isPresent())
                throw new ResourceNotFoundException("Prodotto con ID: " + entryDto.productId() + " non trovato");


            ProductEntity product = productOpt.get();

            // Controllo disponibilità in magazzino
            if (product.getStock() < entryDto.quantity())
                throw new OutOfStockException("Disponibilità insufficiente per il prodotto: " + product.getName() +
                        " (Richiesti: " + entryDto.quantity() + ", Disponibili: " + product.getStock() + ")");


            // Aggiornamento Scorte:
            // Sottraggo la quantità ordinata dallo stock attuale
            product.setStock(product.getStock() - entryDto.quantity());
            this.productRepository.save(product);

            // Creazione entry "richiesta"
            OrderEntryEntity newOrderEntry = OrderEntryEntity.builder()
                    .product(product)
                    .quantity(entryDto.quantity())
                    .price(product.getPrice()) // Salva il prezzo attuale al momento dell'acquisto
                    .total(product.getPrice().multiply(BigDecimal.valueOf(entryDto.quantity())))
                    .build();

            // Aggiorno il totale dell'ordine e aggiungiamo la entry all'ordine
            newOrder.setTotal(newOrder.getTotal().add(newOrderEntry.getTotal()));
            newOrder.addEntry(newOrderEntry);
        }

        // Salvo e restituisco
        this.orderRepository.save(newOrder);
        return this.orderMapper.toDto(newOrder);
    }


    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(UUID orderId, OrderStatus newStatus) {
        // Cerco l'ordine nel DB
        Optional<OrderEntity> orderOptional = this.orderRepository.findById(orderId);
        
        // Se non lo trovo lancio eccezione
        if (!orderOptional.isPresent())
            throw new ResourceNotFoundException("Ordine con ID: " + orderId + " non trovato");

        // Se lo trovo lo estraggo e aggiorno lo stato
        OrderEntity order = orderOptional.get();
        order.setStatus(newStatus);

        // Salvo l'entità aggiornata
        this.orderRepository.save(order);

        // Converto in DTO e restituisco
        return this.orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDTO> getMyOrders() {
        // Recupero l'utente loggato dal contesto di sicurezza
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Recupero la lista di entità dal repository usando l'ID dell'utente autenticato
        List<OrderEntity> ordersList = this.orderRepository.findByUser_Id(currentUser.getId());

        // Converto la lista di entità in lista di DTO e restituisco
        return this.orderMapper.toDtoList(ordersList);
    }

    @Override
    @Transactional
    public OrderResponseDTO cancelOrder(UUID orderId) {
        // Recupero utente loggato
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Cerco l'ordine nel DB
        Optional<OrderEntity> orderOptional = this.orderRepository.findById(orderId);

        // Controllo se l'ordine esiste
        if (!orderOptional.isPresent()) {
            throw new ResourceNotFoundException("Ordine con ID: " + orderId + " non trovato");
        }

        OrderEntity order = orderOptional.get();

        // Controllo proprietà
        // Verifica che l'ID dell'utente dell'ordine sia uguale all'ID dell'utente loggato
        if (!order.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Ordine con ID: " + orderId + " non trovato per questo utente.");
        }

        // Controllo lo stato attuale:
        // Se l'ordine è già SPEDITO o CONSEGNATO, non si può annullare
        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new InvalidOrderStateException("Impossibile annullare l'ordine: è già stato spedito o consegnato.");
        }

        // Se tutto ok, cambio lo stato in CANCELED
        order.setStatus(OrderStatus.CANCELED);

        // Salvo l'entità aggiornata
        this.orderRepository.save(order);

        // Converto in DTO e restituisco
        return this.orderMapper.toDto(order);
    }
}

