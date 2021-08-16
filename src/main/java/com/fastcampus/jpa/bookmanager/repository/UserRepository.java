package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Set<User> findUserByNameIs(String name);
    Set<User> findUserByName(String name);
    Set<User> findUserByNameEquals(String name);

    Optional<User> findByEmail(String email);
    Optional<User> getByName(String name);
    Optional<User> readByName(String name);
    Optional<User> queryByName(String name);
    Optional<User> searchByName(String name);
    Optional<User> streamByName(String name);
    Optional<User> findSomethingByName(String name);
    List<User> findFirst1ByName(String name);
    List<User> findTop1ByName(String name);

    List<User> findByEmailAndName(String email, String name);
    List<User> findByEmailOrName(String email, String name);

    List<User> findByCreatedAtAfter(LocalDateTime yesterday);
    List<User> findByIdAfter(Long id);
    List<User> findByCreatedAtGreaterThan(LocalDateTime yesterday);

    List<User> findByCreatedAtBetween(LocalDateTime yesterday, LocalDateTime tomorrow);

    List<User> findByIdIsNotNull();

    List<User> findByNameIn(List<String> names);

    List<User> findByNameStartingWith(String name);
    List<User> findByNameEndingWith(String name);
    List<User> findByNameContains(String name);

    List<User> findByNameLike(String name);

    List<User> findLast1ByName(String name);

    List<User> findTop1ByNameOrderByIdDesc(String name);

    List<User> findFirstByNameOrderByIdDesc(String name);

    List<User> findFirstByNameOrderByIdDescEmailAsc(String name);

    List<User> findFirstByName(String name, Sort sort);

    Page<User> findByName(String name, Pageable pageable);

    @Query(value = "select * from user limit 1;", nativeQuery = true)
    Map<String, Object> findRawRecord();

}
