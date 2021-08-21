package com.fastcampus.jpa.bookmanager.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

import com.fastcampus.jpa.bookmanager.domain.Gender;
import com.fastcampus.jpa.bookmanager.domain.User;

import jdk.vm.ci.meta.Local;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@WebAppConfiguration
@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

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

        System.out.println("findFirst1ByName : " + userRepository.findFirst1ByName("martin"));
        System.out.println("findTop1ByName : " + userRepository.findTop1ByName("martin"));

        System.out.println("findByEmailAndName : " + userRepository.findByEmailAndName("martin@naver.com","martin"));
        System.out.println("findByEmailOrName : " + userRepository.findByEmailOrName("martin@naver.com","martin"));

        System.out.println("findbyCreatedAtAfter : "+ userRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByCreatedAtGreaterThan : " + userRepository.findByCreatedAtGreaterThan(LocalDateTime.now().minusDays(1L)));

        System.out.println("findByIdIsNotNull : "+ userRepository.findByIdIsNotNull());

        System.out.println("findByNmaeIn : " + userRepository.findByNameIn(Lists.newArrayList("mar")));
    }

    @Test
    void pagingAndSortingTest() {
        System.out.println("findTop1ByName : " + userRepository.findTop1ByName("martin"));
        System.out.println("findTop1ByNameOrderByIdDesc : " + userRepository.findTop1ByNameOrderByIdDesc("martin"));
        System.out.println("findFirstByNameOrderByIdDescEmailAsc" + userRepository.findFirstByNameOrderByIdDescEmailAsc("martin"));
        System.out.println("findFirstByNameWithSortParams : " + userRepository.findFirstByName("martin", Sort.by(Sort.Order.desc("id"))));
        System.out.println("findFirstByNameWithSortParams : " + userRepository.findFirstByName("martin", getSort()));
        System.out.println("findByNameWithPaging : " + userRepository.findByName("martin", PageRequest.of(0, 1, getSort())).getContent());
    }

    private Sort getSort() {
        return Sort.by(
                Sort.Order.desc("id"),
                Sort.Order.desc("email")
        );
    }

    @Test
    void insertAndUpdateTest() {
        User user = new User();
        user.setName("martin");
        user.setEmail("martin22@naver.com");

        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrtttin");

        userRepository.save(user2);

        System.out.println(userRepository.findByName("marrrtttin"));
    }

    @Test
    void enumTest() {
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setGender(Gender.MALE);

        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);

        System.out.println(userRepository.findRawRecord().get("gender"));
    }

    @Test
    void listenerTest() {
        User user = new User();
        user.setEmail("martin22@naver.com");
        user.setName("martin");

        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("maaaaartin");
        userRepository.save(user2);

        userRepository.deleteById(4L);
    }

    @Test
    void prePersistTest() {
        User user = new User();
        user.setEmail("jake2@naver.com");
        user.setName("jake");

        userRepository.save(user);

        System.out.println(userRepository.findByEmail("jake2@naver.com"));
    }

    @Test
    void preUpdateTest() {
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        System.out.println("as-is : "+ user);

        user.setName("martin22312");

        userRepository.save(user);

        System.out.println("to be : " + userRepository.findAll().get(0));
    }

    @Test
    void userHistoryTest() {
        User user = new User();
        user.setEmail("martin-new@naver.com");
        user.setName("martin-new");
        user.setGender(Gender.MALE);
        userRepository.save(user);

        user.setName("martin-new-new");
        userRepository.flush();

        userHistoryRepository.findAll().forEach(System.out::println);

    }

    @Test
    void userRelationTest() {
        User user = new User();
        user.setName("david");
        user.setEmail("david@naver.com");
        user.setGender(Gender.MALE);
        userRepository.save(user);

        user.setName("Jason");
        userRepository.flush();

        user.setName("Jason-new");

        userRepository.flush();

        userHistoryRepository.findAll().forEach(System.out::println);
    }
}
