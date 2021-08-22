package com.fastcampus.jpa.bookmanager.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "buyer_product")
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    public void addProducts(Product... products) {
        Collections.addAll(this.products, products);
    }

}
