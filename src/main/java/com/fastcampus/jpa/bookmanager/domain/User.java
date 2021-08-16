package com.fastcampus.jpa.bookmanager.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

//    @Transient
//    private String testData;

//    @OneToMany(fetch = FetchType.EAGER)
//    private List<Address> address;

    @PrePersist //insert method 호출 전
    public void prePersist() {
        System.out.println(">>>> prePersist");
    }

    @PostPersist //insert method 호출 후
    public void postPersist() {
        System.out.println(">>>> postPersist");
    }

    @PreUpdate //merge method 호출 전
    public void preUpdate() {
        System.out.println(">>> preUpdate");
    }

    @PostUpdate //merge method 호출 이후
    public void postUpdate() {
        System.out.println(">>> postUpdate");
    }

    @PreRemove //delete method 호출 전
    public void preRemove() {
        System.out.println(">>> preRemove");
    }

    @PostRemove //delete method 호출 후
    public void postRemove() {
        System.out.println(">>> postRemove");
    }

    @PostLoad // select method 호출 후
    public void postLoad() {
        System.out.println(">>> postLoad");
    }

}
