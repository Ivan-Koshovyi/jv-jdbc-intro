package mate.academy.dao;

import mate.academy.ConnectionUntil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao{
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try(Connection connection = ConnectionUntil.getConection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


        } catch (SQLException e) {
            throw new RuntimeException("Can not create a connection to the DB", e);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return List.of();
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
