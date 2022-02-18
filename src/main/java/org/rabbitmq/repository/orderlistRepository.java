package org.rabbitmq.repository;

import javax.persistence.Table;

import org.rabbitmq.model.orderlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(name = "webapp_orderlist")
public interface orderlistRepository extends JpaRepository<orderlist, String>  {
  
}

