package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Buyer> buyers = new ArrayList<>();

    public void addBuyers(Buyer... buyers) {
        Collections.addAll(this.buyers, buyers);
    }

}
