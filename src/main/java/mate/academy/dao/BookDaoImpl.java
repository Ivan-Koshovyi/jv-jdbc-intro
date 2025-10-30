package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConection();
                PreparedStatement statement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at "
                        + "leas one row, but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not add a new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT title, price FROM books WHERE id = ?";
        Optional<Book> book = null;
        try (Connection connection = ConnectionUtil.getConection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                book = Optional.of(new Book.Builder()
                        .setId(id)
                        .setTitle(title)
                        .setPrice(price)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not select a book with id: " + id, e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> listBook = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                listBook.add(new Book.Builder().setId(id).setTitle(title).setPrice(price).build());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not select books", e);
        }
        return listBook;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at "
                        + "leas one row, but update 0 rows.");
            }

            return findById(book.getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Book not found after update, id = " + book.getId()));
        } catch (SQLException e) {
            throw new RuntimeException("Can not update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete book with id: " + id, e);
        }
    }
}
