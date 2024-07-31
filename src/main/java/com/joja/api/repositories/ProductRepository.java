package com.joja.api.repositories;

import com.joja.api.models.ProductModel;
import com.joja.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findByUser(UserModel user);
}
