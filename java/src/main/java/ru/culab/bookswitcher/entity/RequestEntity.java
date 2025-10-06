package ru.culab.bookswitcher.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestEntity {

    public static enum Status {
        SENT, REJECTED, GAVE, RETURNED
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long userId;

    @Column
    private long bookId;

    @Column
    private Status status;
}
