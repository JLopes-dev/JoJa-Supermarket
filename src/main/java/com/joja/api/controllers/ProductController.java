package com.joja.api.controllers;

import com.joja.api.DTOs.DTOProduct;
import com.joja.api.DTOs.DTOProductUpdated;
import com.joja.api.models.ProductModel;
import com.joja.api.models.UserModel;
import com.joja.api.repositories.ProductRepository;
import com.joja.api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @PostMapping("/{username}")
    public ResponseEntity<DTOProduct> createProduct(@RequestBody @Valid DTOProduct dtoProduct, @PathVariable String username ,UriComponentsBuilder uriComponentsBuilder){
        UserModel user = (UserModel) userRepository.findByUsername(username);
        if (user != null){
            ProductModel productSaved = productRepository.save(new ProductModel(dtoProduct));
            productSaved.setUser(user);
            URI uri = uriComponentsBuilder.path("/product/{id}").buildAndExpand(productSaved.getId()).toUri();
            return ResponseEntity.created(uri).body(new DTOProduct(productSaved.getId(), productSaved.getName(), productSaved.getPrice(), productSaved.getQuantity()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<DTOProduct>> showAllProductsByUser(@PathVariable String username){
        UserModel user = (UserModel) userRepository.findByUsername(username);
        if (user != null){
            List<DTOProduct> products = productRepository.findByUser(user).stream().map(value -> new DTOProduct(value.getId(), value.getName(), value.getPrice(), value.getQuantity())).toList();
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DTOProduct> updateProduct(@RequestBody @Valid DTOProductUpdated dtoProductUpdated, @PathVariable Long id){
        ProductModel product = productRepository.getReferenceById(id);
        product.updatedProduct(dtoProductUpdated);
        return ResponseEntity.ok(new DTOProduct(product.getId(), product.getName(), product.getPrice(), product.getQuantity()));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
