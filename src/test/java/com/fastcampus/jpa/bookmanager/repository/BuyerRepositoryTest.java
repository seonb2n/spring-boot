package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Buyer;
import com.fastcampus.jpa.bookmanager.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuyerRepositoryTest {
    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void ManyToManyTest() {
        Product p1 = givenProduct("p1");
        Product p2 = givenProduct("p2");
        Product p3 = givenProduct("p3");

        Buyer b1 = givenBuyer("Jake", p1, p2);
        buyerRepository.save(b1);

        Buyer b2 = givenBuyer("Mike", p1, p3);
        buyerRepository.save(b2);

        p1.addBuyers(b1, b2);
        p2.addBuyers(b1);
        p3.addBuyers(b2);

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);

//        buyerRepository.findAll().forEach(System.out::println);

        productRepository.findAll().forEach(System.out::println);
    }

    private Buyer givenBuyer(String name, Product... products) {
        Buyer buyer = new Buyer();
        buyer.setName(name);
        buyer.addProducts(products);
        return buyerRepository.save(buyer);
    }

    private Product givenProduct(String name) {
        Product product = new Product();
        product.setName(name);
        return productRepository.save(product);
    }
}