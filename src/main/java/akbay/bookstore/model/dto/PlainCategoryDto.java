package akbay.bookstore.model.dto;

import akbay.bookstore.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlainCategoryDto {
    private Long id;
    private String name;

    public static PlainCategoryDto from(Category category)
    {
        PlainCategoryDto plainCategoryDto = new PlainCategoryDto();
        plainCategoryDto.setId(category.getId());
        plainCategoryDto.setName(category.getName());
        return plainCategoryDto;
    }
}
