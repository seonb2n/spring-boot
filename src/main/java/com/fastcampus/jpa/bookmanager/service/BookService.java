package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.Author;
import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.repository.AuthorRepository;
import com.fastcampus.jpa.bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.beans.ExceptionListener;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    //생성자 주입. final 이므로 생성자를 자동으로 생성해줌. bean 이 생성되면서 주입된다.
    private final EntityManager entityManager;
    private final AuthorService authorService;


    @Transactional(propagation = Propagation.REQUIRED)
    void putBookAndAuthor(){
        Book book = new Book();
        book.setName("Start JPA");

        bookRepository.save(book);

        try {
            authorService.putAuthor();
        } catch (RuntimeException e) {

        }
//        throw new RuntimeException("Error !! Transaction error occurs");

//        Author author = new Author();
//        author.setName("martin");
//
//        authorRepository.save(author);
//
//        throw new RuntimeException("Error !! Do not commit to db");
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void get(Long id) {
        System.out.println(">>> "+ bookRepository.findById(id));
        System.out.println(">>> "+ bookRepository.findAll());

        entityManager.clear();

        System.out.println(">>> "+ bookRepository.findById(id));
        System.out.println(">>> "+ bookRepository.findAll());

        bookRepository.update();

        entityManager.clear();

//        Book book = bookRepository.findById(id).get();
//        book.setName("Changed?");
//        bookRepository.save(book);
    }

    @Transactional
    public List<Book> getAll() {
        List<Book> books = bookRepository.findAll();

        books.forEach(System.out::println);

        return books;
    }

}
