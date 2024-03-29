package com.springboot.books.dao.impl;

import com.springboot.books.domain.Author;
import com.springboot.books.dao.AuthorDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// We need to put the @Component annotation here because we need to tell spring to create a bean for us.
@Component
public class AuthorDaoImpl implements AuthorDao {

    // We need the JDBC Template here. So, We use dependency injection (Auto wiring) here. ( From the bean created in the config class )
    // We make this final because to make this cannot be reassigned once it instantiated.
    private final JdbcTemplate jdbcTemplate;
    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update(
                "INSERT into authors (id, name, age) VALUES (?,?,?)",
                author.getId(), author.getName(), author.getAge()
        );
    }

    @Override
    public Optional<Author> findOne(long authorId) {
        // We get the resulting objects into a list.
        List<Author> results =  jdbcTemplate.query(
                "SELECT id, name , age FROM authors WHERE id = ? LIMIT 1",
                new AuthorRowMapper(),
                authorId
                );

        // To find the first optional and return we can use the stream().findFirst()
        return results.stream().findFirst();
    }

    @Override
    public List<Author> find() {
        // We get the resulting objects into a list.
        List<Author> results = jdbcTemplate.query(
                "SELECT id, name, age FROM authors",
                new AuthorRowMapper()
        );

        return results;
    }

    @Override
    public void update(Author author, Long id) {

        jdbcTemplate.update(
                "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                author.getId(),author.getName(),author.getAge(),id
        );

    }

    @Override
    public void delete(long authorId) {

        jdbcTemplate.update(
                "DELETE FROM authors WHERE id = ?",
                authorId
        );

    }


    // This is a nested class for the RowMapper.
    // RowMapper creates objects for results we get from the Database.
    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }

}
