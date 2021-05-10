package akbay.bookstore.model.dto;

import akbay.bookstore.model.Book;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Long id;

    @NonNull
    private String name;
    private BigDecimal price;
    private PlainCategoryDto plainCategoryDto;

    public static BookDto from(Book book)
    {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setPrice(book.getPrice());
        if(Objects.nonNull(book.getCategory()))
        {
            bookDto.setPlainCategoryDto(PlainCategoryDto.from(book.getCategory()));
        }

        return bookDto;

    }

}
