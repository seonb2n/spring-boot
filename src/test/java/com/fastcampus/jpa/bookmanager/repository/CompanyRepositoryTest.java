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
    void OneToManyTest() {
        Company company = givenCompany("New Com");
        Product p1 = givenProduct("product1", company);
        Product p2 = givenProduct("product2", company);
        Product p3 = givenProduct("product3", company);

        company.addProducts(p1, p2, p3);
        companyRepository.saveAndFlush(company);

        productRepository.findAll().forEach(System.out::println);
        System.out.println(companyRepository.findById(1L).get().getProducts());
    }

    private Company givenCompany(String name) {

        Company company = new Company();
        company.setName(name);
        return companyRepository.save(company);
    }

    private Blog givenBlog(String content) {
        Blog blog = new Blog();
        blog.setContent(content);
        return blogRepository.save(blog);
    }

    private Product givenProduct(String name, Company company) {
        Product product = new Product();
        product.setName(name);
        product.setCompany(company);
        return productRepository.save(product);
    }

}