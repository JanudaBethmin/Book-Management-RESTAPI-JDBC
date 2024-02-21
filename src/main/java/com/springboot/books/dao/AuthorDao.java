package com.springboot.books.dao;

import com.springboot.books.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    // Optional is a container either it has something init or don't.
    // If we try to return an object from the database if the object is not in the database  it returns null.
    // After we retrieve that object we normally use methods on it.
    // But we can't call methods on when the event of not finding the object. This results a null pointer exception.
    // For this situation we use Optionals.
    // Optional is like a box. We find the object in the database the object will be put into the box and returned.
    // Otherwise, only the box will be returned.
    Optional<Author> findOne(long authorId);

    // Find will return a of Author objects.
    List<Author> find();

    void update(Author author, Long id);

    void delete(long authorId);
}
