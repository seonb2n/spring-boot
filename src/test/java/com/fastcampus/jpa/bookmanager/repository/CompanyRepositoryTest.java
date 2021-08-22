package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Blog;
import com.fastcampus.jpa.bookmanager.domain.Company;
import com.fastcampus.jpa.bookmanager.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BlogRepository blogRepository;

    @Test
    void OneToOneTest() {
        givenCompany("New Com", givenBlog("New com's good blog"));
        Company company = companyRepository.findById(1L).get();
        Blog blog = company.getBlog();
        blog.setCompany(company);
        blogRepository.saveAndFlush(blog);

        System.out.println(blog);
        System.out.println(company);
    }

    private Company givenCompany(String name, Blog blog) {
        Company company = new Company();
        company.setName(name);
        company.setBlog(blog);
        return companyRepository.save(company);
    }

    private Blog givenBlog(String content) {
        Blog blog = new Blog();
        blog.setContent(content);
        return blogRepository.save(blog);
    }

}