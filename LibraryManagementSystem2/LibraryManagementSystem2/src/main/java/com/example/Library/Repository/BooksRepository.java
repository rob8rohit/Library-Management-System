package com.example.Library.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Library.Entity.Books;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books,Long> {
    List<Books> findByAuthorContainingIgnoreCase(String author);

    List<Books> findByCategoryContainingIgnoreCase(String category);

    List<Books> findByTitleContainingIgnoreCase(String title);
}
