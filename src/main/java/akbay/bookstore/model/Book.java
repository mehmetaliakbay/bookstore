package akbay.bookstore.model;

import akbay.bookstore.model.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book", uniqueConstraints = @UniqueConstraint(columnNames = {"name","price"}))
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "bookstore_id")
    private BookStore bookStore;


    public static Book from(BookDto bookDto)
    {
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setPrice(bookDto.getPrice());
        if(Objects.nonNull(bookDto.getPlainCategoryDto()))
        {
            Category category = new Category();
            category.setId(bookDto.getPlainCategoryDto().getId());
            category.setName(bookDto.getPlainCategoryDto().getName());
            book.setCategory(category);

        }
        return book;
    }
}
