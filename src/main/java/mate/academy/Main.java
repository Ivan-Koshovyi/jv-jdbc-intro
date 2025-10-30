package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book.Builder()
                .setTitle("The Pragmatic Programmer")
                .setPrice(new BigDecimal("45.99"))
                .build();

        System.out.println(bookDao.create(book));

        Optional<Book> bookForId = bookDao.findById(7L);
        System.out.println(bookForId);

        List<Book> all = bookDao.findAll();
        all.forEach(System.out::println);

        System.out.println(bookDao.deleteById(7L));

        bookDao.update(new Book.Builder()
                .setId(3L)
                .setTitle("Чистий код")
                .setPrice(new BigDecimal("655.99"))
                .build());
        bookDao.findAll().forEach(System.out::println);
    }
}
