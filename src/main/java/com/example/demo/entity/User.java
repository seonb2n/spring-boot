package com.example.demo.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
