package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.util.List;
import java.util.Optional;

@Dao
public interface BookDao {
    Book create(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id);
}
