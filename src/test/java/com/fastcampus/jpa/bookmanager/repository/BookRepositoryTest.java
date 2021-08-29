package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.domain.Publisher;
import com.fastcampus.jpa.bookmanager.domain.Review;
import com.fastcampus.jpa.bookmanager.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void bookTest() {
        Book book = new Book();
        book.setName("JPA LEARNING");
        book.setAuthorId(1L);

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }

    @Test
    @Transactional
    void bookRelationTest() {
        givenBookAndReview();

        User user = userRepository.findByEmail("martin@fastcampus.com");

        System.out.println("Review : " + user.getReviews());
        System.out.println("Book : " + user.getReviews().get(0).getBook());
        System.out.println("Publisher : "+ user.getReviews().get(0).getBook().getPublisher());
    }

    @Transactional //lazily loading 방지
    @Test
    void bookCascadeTest() {
        Book book = new Book();
        book.setName("JPA Course");

        Publisher publisher = new Publisher();
        publisher.setName("fastcampus");

        book.setPublisher(publisher);
        bookRepository.save(book);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers : "+ publisherRepository.findAll());

        Book book1 = bookRepository.findById(1L).get();
        book1.getPublisher().setName("slowcampus");

        bookRepository.save(book1);

        System.out.println("publisher : " + publisherRepository.findAll());
    }


    private void givenBookAndReview() {
        givenReview(givenUser(), givenBook(givenPublisher()));
    }

    private User givenUser() {
        return userRepository.findByEmail("martin@fastcampus.com");
    }

    private Book givenBook(Publisher publisher) {
        Book book = new Book();
        book.setName("JPA Package");
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }

    private Publisher givenPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("naver");
        return publisherRepository.save(publisher);
    }

    private void givenReview(User user, Book book) {
        Review review = new Review();
        review.setTitle("my best book");
        review.setContent("Very fun and good book");
        review.setScore(5.0f);
        review.setUser(user);
        review.setBook(book);

        reviewRepository.save(review);
    }
}
