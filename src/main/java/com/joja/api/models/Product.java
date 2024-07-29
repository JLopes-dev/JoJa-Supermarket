package com.joja.api.models;

import com.joja.api.DTOs.DTOProduct;
import com.joja.api.DTOs.DTOProductUpdated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "products")
@Entity(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Integer quantity;

    public Product(DTOProduct dtoProduct) {
        this.name = dtoProduct.name();
        this.price = dtoProduct.price();
        this.quantity = dtoProduct.quantity();
    }

    public void updatedProduct(DTOProductUpdated dtoProductUpdated){
        if (dtoProductUpdated.name() != null){
            this.name = dtoProductUpdated.name();
        }
        if (dtoProductUpdated.price() != null){
            this.price = dtoProductUpdated.price();
        }
        if (dtoProductUpdated.quantity() != null){
            this.quantity = dtoProductUpdated.quantity();
        }
    }
}
