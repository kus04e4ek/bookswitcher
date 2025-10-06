package ru.culab.bookswitcher.entity;

import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long userId;

    @Column
    private long holderId;

    @NotEmpty
    @Column
    private String title;

    @NotEmpty
    @Column
    private String author;

    @ElementCollection
    @Column
    private Set<Long> requestUsers;

    public boolean addRequestUser(long userId) {
        return requestUsers.add(userId);
    }
}
