package com.joja.api.controllers;

import com.joja.api.DTOs.DTOProduct;
import com.joja.api.DTOs.DTOProductUpdated;
import com.joja.api.models.Product;
import com.joja.api.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Transactional
    @PostMapping
    public ResponseEntity<DTOProduct> createProduct(@RequestBody @Valid DTOProduct dtoProduct, UriComponentsBuilder uriComponentsBuilder){
        Product productSaved = repository.save(new Product(dtoProduct));
        URI uri = uriComponentsBuilder.path("/product/{id}").buildAndExpand(productSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTOProduct(productSaved.getName(), productSaved.getPrice(), productSaved.getQuantity()));
    }

    @GetMapping
    public ResponseEntity<Page<Product>> showALlProducts(@PageableDefault(size = 20, sort = "name") Pageable pageable){
        Page<Product> products = repository.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DTOProduct> updateProduct(@RequestBody @Valid DTOProductUpdated dtoProductUpdated, @PathVariable Long id){
        Product product = repository.getReferenceById(id);
        product.updatedProduct(dtoProductUpdated);
        return ResponseEntity.ok(new DTOProduct(product.getName(), product.getPrice(), product.getQuantity()));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
