package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private EntityManager entityManager;
//    @Autowired
//    private UserRepository userRepository;

    @Transactional
    public void put() {
        User user = new User();
        user.setName("newUser");
        user.setEmail(("newUser@naver.com"));
        //user 객체는 비영속 상태이다.

        //userRepository.save(user);

        entityManager.persist(user);
        
        user.setName("newUserAfterPersist");

        entityManager.merge(user);

        entityManager.remove(user);
        
    }
}
