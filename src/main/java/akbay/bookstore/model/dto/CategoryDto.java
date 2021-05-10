package akbay.bookstore.model.dto;

import akbay.bookstore.model.Category;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;

    @NonNull
    private String name;
    private Set<BookDto> booksDto = new HashSet<>();

    public static CategoryDto from(Category category)
    {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setBooksDto(category.getBooks().stream().map(BookDto::from).collect(Collectors.toSet()));
        return categoryDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDto that = (CategoryDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(booksDto, that.booksDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, booksDto);
    }
}
