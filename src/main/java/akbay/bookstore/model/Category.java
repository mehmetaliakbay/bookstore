package akbay.bookstore.model;

import akbay.bookstore.model.dto.CategoryDto;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book)
    {
        books.add(book);
    }

    public void removeBook(Book book)
    {
        books.remove(book);
        book.setCategory(null);
    }

    public static Category from(CategoryDto categoryDto)
    {
        Category category= new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setBooks(categoryDto.getBooksDto().stream().map(Book::from).collect(Collectors.toSet()));
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(books, category.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, books);
    }
}
