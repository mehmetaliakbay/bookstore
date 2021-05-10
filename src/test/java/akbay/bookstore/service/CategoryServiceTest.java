package akbay.bookstore.service;

import akbay.bookstore.model.Category;
import akbay.bookstore.model.exception.CategoryNotFoundException;
import akbay.bookstore.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookService bookService;


    @Test
    void testAddCategory() {
        Category category = new Category(1L,"testCategoryName",null);
        when(categoryRepository.save(category)).thenReturn(category);
        assertEquals(category,categoryService.addCategory(category));

    }

    @Test
    void testDuplicatedBookStoreException() {

        Category category = new Category(1L,"testCategoryName",null);
        when(categoryRepository.save(category)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class,() -> categoryService.addCategory(category));
    }
    @Test
    void testGetCategories() {

        when(categoryRepository.findAll()).thenReturn(Stream.of(
                new Category(1L, "testCategoryName1", null),
                new Category(2L, "testCategoryName2", null)
        ).collect(Collectors.toList()));
        assertEquals(2,categoryService.getCategories().size());

    }


    @Test
    void testAddBookToCategory() {

        Long id = 1L;
        assertThat(assertThrows(CategoryNotFoundException.class,() -> categoryService.getCategory(id)))
                .hasMessage("Could not find category with id: 1");
    }

    @Test
    void removeBookFromCategory() {

        Long categoryId = 1L;
        Long bookId = 3L;
        assertThat(assertThrows(CategoryNotFoundException.class,() -> categoryService.removeBookFromCategory(categoryId,bookId)))
                .hasMessage("Could not find category with id: 1");
    }

    @Test
    void getBooksAccordingToCategory() {
        Long bookId = 1L;
        assertThat(assertThrows(CategoryNotFoundException.class,() -> categoryService.getBooksAccordingToCategory(bookId)))
                .hasMessage("Could not find category with id: 1");
    }

    @Test
    void changeBookCategory() {
        Long bookId = 1L;
        Long categoryId = 1L;
        assertThat(assertThrows(CategoryNotFoundException.class,() -> categoryService.changeBookCategory(bookId,categoryId)))
                .hasMessage("Could not find category with id: 1");
    }
}