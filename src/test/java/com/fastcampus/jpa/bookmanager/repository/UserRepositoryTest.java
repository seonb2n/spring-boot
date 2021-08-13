package com.fastcampus.jpa.bookmanager.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

import com.fastcampus.jpa.bookmanager.domain.User;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@WebAppConfiguration
@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() { // create, read, update, delete
        userRepository.save(new User("jake", "jake12@naver.com"));

        User user = userRepository.findById(1L).orElse(null);
        user.setEmail("martin-new@naver.com");

        userRepository.save(user);

    }

    @Test
    void select() {

        System.out.println("findByName : " + userRepository.findByName("dennis"));
        System.out.println("getByName : " + userRepository.getByName("dennis"));
        System.out.println("readByName : " + userRepository.readByName("dennis"));
        System.out.println("queryByName : " + userRepository.queryByName("dennis"));
        System.out.println("searchByName : " + userRepository.searchByName("dennis"));
        System.out.println("streamByName : " + userRepository.streamByName("dennis"));
        System.out.println("findUserByName : " + userRepository.findUserByName("dennis"));
        System.out.println("findSomethingByName : " + userRepository.findSomethingByName("dennis"));

    }
}
