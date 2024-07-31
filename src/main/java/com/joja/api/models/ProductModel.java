package com.joja.api.models;

import com.joja.api.DTOs.DTOProduct;
import com.joja.api.DTOs.DTOProductUpdated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
@Entity(name = "Product")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    public ProductModel(DTOProduct dtoProduct) {
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
