package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Transactional
    void reviewTest(){
//        List<Review> reviews = reviewRepository.findAllByFetchJoin();

        List<Review> reviews = reviewRepository.findAllByEntityGraph();

//        System.out.println(reviews);

//        System.out.println("Bring All");
//
//        System.out.println(reviews.get(0).getComments());
//
//        System.out.println("첫 번째 리뷰의 코멘트");
//
//        System.out.println(reviews.get(1).getComments());
//
//        System.out.println("두 번째 리뷰의 코멘트");

        reviews.forEach(System.out::println);
    }
}
