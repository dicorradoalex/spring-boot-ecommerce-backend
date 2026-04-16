package com.lipari.Academy2026.repository;

import com.lipari.Academy2026.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    // Grazie a JpaRepository: save(), findById(), findAll(), deleteById()

    List<OrderEntity> findByUser_Id(UUID userId);

}
