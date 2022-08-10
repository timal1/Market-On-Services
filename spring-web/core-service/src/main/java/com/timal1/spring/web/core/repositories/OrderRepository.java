package com.timal1.spring.web.core.repositories;

import com.timal1.spring.web.core.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.username = ?1 and o.status NOT Like 'CANCELED'")
    List<Order> findAllByUsername(String username);


    @Query(value = "select p.title from (select oi.product_id,  sum(oi.quantity) \n" +
            "from order_items as oi WHERE oi.created_at >= date_trunc('day', NOW() - interval '5' day) \n" +
            "and oi.created_at <= NOW() group by oi.product_id order by sum desc limit :p2) \n" +
            "as max_quantity left join products p \n" +
            "ON max_quantity.product_id=p.ID order by max_quantity.product_id desc",  nativeQuery = true)
    List<String> findFavoritesProducts(@Param("p2") int amountProducts);

}
