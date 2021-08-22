package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "compnay_id")
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    public void addProducts(Product... products) {
        Collections.addAll(this.products, products);
    }

}
