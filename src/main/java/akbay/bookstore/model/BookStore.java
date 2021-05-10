package akbay.bookstore.model;

import akbay.bookstore.model.dto.BookStoreDto;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookstore")
public class BookStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "bookStore")
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setBookStore(null);
    }

    public static BookStore from(BookStoreDto bookStoreDto) {
        BookStore bookStore = new BookStore();
        bookStore.setId(bookStoreDto.getId());
        bookStore.setName(bookStoreDto.getName());
        bookStore.setBooks(bookStoreDto.getBooksDto().stream().map(Book::from).collect(Collectors.toSet()));
        return bookStore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookStore bookStore = (BookStore) o;
        return Objects.equals(id, bookStore.id) && Objects.equals(name, bookStore.name) && Objects.equals(books, bookStore.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, books);
    }
}
