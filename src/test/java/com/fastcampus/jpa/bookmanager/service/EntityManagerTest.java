package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
//@Transactional
public class EntityManagerTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void entityManagerTest() {
        System.out.println(entityManager.createQuery("select u from User u").getResultList());
        //jpa 에서 지원 안하는 기능을 직접 사용하고자 entity manager 활용
    }

    @Test
    void cacheFindTest() {
        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
        System.out.println(userRepository.findById(2L).get());
        System.out.println(userRepository.findById(2L).get());
        System.out.println(userRepository.findById(2L).get());
        //하나의 transaction 으로 실행하면, 하나의 쿼리만 실행되고, 나머지는 캐시 처리된다.
        //1차 캐쉬의 활용으로 빈번한 아이디 조회를 막아 성능 저하 이슈를 막을 수 있다.
        userRepository.deleteById(1L);
    }

    @Test
    void cacheFindTest2() {
        User user = userRepository.findById(1L).get();
        user.setName("marrrrrrrtin");

        userRepository.save(user);

        user.setEmail("martin-new@naver.com");

        userRepository.save(user);

        userRepository.flush();
        //@Transactional 로 인해서 값이 별개로 save 되는 게 아니라, flush 로 한번에 db에 업데이트된다.
    }

}
