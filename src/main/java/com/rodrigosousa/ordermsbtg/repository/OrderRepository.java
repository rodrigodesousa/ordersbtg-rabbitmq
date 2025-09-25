package com.rodrigosousa.ordermsbtg.repository;

import com.rodrigosousa.ordermsbtg.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
}
