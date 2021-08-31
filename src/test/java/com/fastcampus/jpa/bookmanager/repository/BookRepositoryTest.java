package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.domain.Publisher;
import com.fastcampus.jpa.bookmanager.domain.Review;
import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.dto.BookStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        book1.setName("new-book!!");

        bookRepository.save(book1);

        System.out.println("publisher : " + publisherRepository.findAll());

        Book book3 = bookRepository.findById(1L).get();
        book3.setPublisher(null);
        //연관관계가 끊어질 때 삭제를 하지는 않음
        //orphan removal = true 를 통해서 연관관계가 끊어지면 삭제되게 설정할 수 있다.

        bookRepository.save(book3);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers : " + publisherRepository.findAll());
        System.out.println("book3-publisher : "+bookRepository.findById(1L).get().getPublisher());

    }

    @Test
    @Transactional
    void bookRemoveCascadeTest() {
        bookRepository.deleteById(1L);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers " + publisherRepository.findAll());

        bookRepository.findAll().forEach(book -> System.out.println(book.getPublisher()));
    }

    @Test
    void softDelete() {
        bookRepository.findAll().forEach(System.out::println);

//        bookRepository.findByCategoryIsNull().forEach(System.out::println);
//        bookRepository.findAllByDeletedFalse().forEach(System.out::println);
//        bookRepository.findByCategoryIsNullAndDeletedFalse().forEach(System.out::println);
    }

    @Test
    void queryTest() {
        bookRepository.findAll().forEach(System.out::println);
        System.out.println(">>>" +
                bookRepository.findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual(
                        "JPA COURSE",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L)
                ));

        System.out.println("findByNameRecently : " + bookRepository.findByNameRecently(
                "JPA COURSE",
                LocalDateTime.now().minusDays(1L),
                LocalDateTime.now().minusDays(1L)
        ));

        bookRepository.findBookNameAndCategory().forEach(b ->
        {System.out.println(b.getName()+ " : " + b.getCategory());});

        bookRepository.findBookNameAndCategory(PageRequest.of(
                1, 1))
                .forEach(bookNameAndCategory ->
                {System.out.println(bookNameAndCategory.getName() + ": "+bookNameAndCategory.getCategory());});
    }

    @Test
    void nativeQueryTest() {
//        bookRepository.findAll().forEach(System.out::println);
//
//        bookRepository.findAllCustom().forEach(System.out::println);
//        //entity 의 설정이 query 문에 영향을 미치지 못함. 그냥 query 문 자체만 사용된다.

        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            book.setCategory("IT major");
        }

        bookRepository.saveAll(books);
        //book 하나하나를 찾아서 update 를 한다.

        System.out.println("affected rows : " + bookRepository.updateCategories());
        bookRepository.findAllCustom().forEach(System.out::println);

        System.out.println(bookRepository.showTables());
    }

    @Test
    void converterTest() {
        bookRepository.findAll().forEach(System.out::println);

        Book book = new Book();
        book.setName("Converter Test ");
        book.setStatus(new BookStatus(200));
        bookRepository.save(book);

        System.out.println(bookRepository.findRawRecord().values());
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
