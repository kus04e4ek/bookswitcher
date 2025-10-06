package ru.culab.bookswitcher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.culab.bookswitcher.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByUserId(long userId);

    @Query("SELECT b FROM BookEntity b WHERE (:title IS NULL OR b.title ILIKE %:title%) AND (:author IS NULL OR b.author ILIKE %:author%)")
    List<BookEntity> findByFilters(String title, String author);

    @Query("SELECT b FROM BookEntity b WHERE b.userId = b.holderId AND (:title IS NULL OR b.title ILIKE %:title%) AND (:author IS NULL OR b.author ILIKE %:author%)")
    List<BookEntity> findAvailableByFilters(String title, String author);
}
