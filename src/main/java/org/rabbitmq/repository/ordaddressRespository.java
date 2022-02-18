package org.rabbitmq.repository;
import javax.persistence.Table;

import org.rabbitmq.model.ordaddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(name = "webapp_ordaddress")
public interface ordaddressRespository extends JpaRepository<ordaddress, String>  {
 
}

