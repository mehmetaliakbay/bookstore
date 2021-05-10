package akbay.bookstore.model.dto;

import akbay.bookstore.model.BookStore;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookStoreDto {
    private Long id;
    @NonNull
    private String name;
    private Set<BookDto> booksDto = new HashSet<>();


    public static BookStoreDto from(BookStore bookStore)
    {
        BookStoreDto bookStoreDto = new BookStoreDto();
        bookStoreDto.setId(bookStore.getId());
        bookStoreDto.setName(bookStore.getName());
        bookStoreDto.setBooksDto(bookStore.getBooks().stream().map(BookDto::from).collect(Collectors.toSet()));
        return bookStoreDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookStoreDto that = (BookStoreDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(booksDto, that.booksDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, booksDto);
    }
}
