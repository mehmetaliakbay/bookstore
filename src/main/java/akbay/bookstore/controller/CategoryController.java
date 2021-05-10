package akbay.bookstore.controller;

import akbay.bookstore.model.Category;
import akbay.bookstore.model.dto.BookDto;
import akbay.bookstore.model.dto.CategoryDto;
import akbay.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Create a category
     * @param categoryDto category dto
     * @return  category dto and HttpStatus
     */
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody final CategoryDto categoryDto) {
        Category category = categoryService.addCategory(Category.from(categoryDto));
        return new ResponseEntity<>(CategoryDto.from(category), HttpStatus.OK);
    }

    /**
     * List all categories
     * @return  category dtos and HttpStatus
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        List<CategoryDto> categoryDtos = categories.stream().map(CategoryDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }


    /**
     * Add a book to Category
     * @param categoryId   category id which will book's category
     * @param bookId   book id
     * @return category dto and HttpStatus
     */
    @PostMapping(value = "{categoryId}/books/{bookId}/add")
    public ResponseEntity<CategoryDto> addBookToCategory(@PathVariable final Long categoryId,
                                                         @PathVariable final Long bookId) {
        Category category = categoryService.addBookToCategory(categoryId, bookId);
        return new ResponseEntity<>(CategoryDto.from(category), HttpStatus.OK);

    }

    /**
     * Remove a book from category
     * @param categoryId    category id
     * @param bookId    book id
     * @return  category dto and HttpStatus
     */
    @DeleteMapping(value = "{categoryId}/books/{bookId}/remove")
    public ResponseEntity<CategoryDto> removeBookFromCategory(@PathVariable final Long categoryId,
                                                         @PathVariable final Long bookId) {
        Category category = categoryService.removeBookFromCategory(categoryId,bookId);
        return new ResponseEntity<>(CategoryDto.from(category), HttpStatus.OK);
    }

    /**
     * List books according to category
     * @param categoryId    category id
     * @return  books dto and HttpResponse
     */
    @GetMapping(value = "{categoryId}/books")
    public ResponseEntity<List<BookDto>> getBooksAccordingToCategory(@PathVariable final Long categoryId)
    {
        List<BookDto> bookDtos = categoryService.getBooksAccordingToCategory(categoryId).stream().map(BookDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(bookDtos,HttpStatus.OK);
    }


}
