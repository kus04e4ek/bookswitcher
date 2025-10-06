package ru.culab.bookswitcher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.culab.bookswitcher.entity.RequestEntity;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

    List<RequestEntity> findByUserId(long userId);

    List<RequestEntity> findByBookId(long bookId);

    boolean existsByUserIdAndBookId(long userId, long bookId);
}
