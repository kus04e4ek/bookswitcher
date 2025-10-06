package ru.culab.bookswitcher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.culab.bookswitcher.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByUserId(long userId);

    List<ReviewEntity> findByBookId(long bookId);

    boolean existsByUserIdAndBookId(long userId, long bookId);
}
