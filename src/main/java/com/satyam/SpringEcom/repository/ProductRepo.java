package com.satyam.SpringEcom.repository;

import com.satyam.SpringEcom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {


    @Query("SELECT p FROM Product p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :key, '%')) " +//its JPL query langauage not sql ,: is used to tell its a variable
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :key, '%')) " +
            "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :key, '%')) " +
            "OR LOWER(p.category) LIKE LOWER(CONCAT('%', :key, '%'))")
    List<Product> searchProducts( String key);

}
