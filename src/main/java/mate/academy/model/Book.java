package mate.academy.model;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private String title;
    private BigDecimal price;

    private Book() {
        // приватний конструктор, щоб не можна було створити напряму
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", price=" + price
                + '}';
    }

    // 🔧 Статичний внутрішній клас Builder
    public static class Builder {
        private Long id;
        private String title;
        private BigDecimal price;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Book build() {
            Book book = new Book();
            book.id = this.id;
            book.title = this.title;
            book.price = this.price;
            return book;
        }
    }
}
