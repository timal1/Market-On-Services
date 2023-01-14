package com.timal1.spring.web.cart.repositories;

import com.timal1.spring.web.cart.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<CartItemEntity, Long> {

    @Query(value = "select max_quantity.product_title from (select ci.product_title,  count(ci.product_id)  from cart_items as ci WHERE ci.created_at >= date_trunc('day', NOW() - interval '5' day) \n" +
            "and ci.created_at < NOW() group by ci.product_title order by count desc limit 5) as max_quantity",  nativeQuery = true)
    List<String> findFavoritesProducts(@Param("p2") int amountProducts, @Param("p3") int amountDay);

}
