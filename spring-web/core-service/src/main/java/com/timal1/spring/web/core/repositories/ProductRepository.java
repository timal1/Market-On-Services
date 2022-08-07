package com.timal1.spring.web.core.repositories;

import com.timal1.spring.web.core.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

   @Query(value = "select p.title from Product p where p.id in (?1)")
   List<String> findByListId(List<Long> listId);
}
