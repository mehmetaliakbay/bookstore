package akbay.bookstore.controller;

import akbay.bookstore.model.Book;
import akbay.bookstore.model.dto.BookDto;
import akbay.bookstore.service.BookService;
import akbay.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }


    /**
     * Create a book
     * @param bookDto book represent object
     * @return  response book represent object and HttpStatus
     */
    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody final BookDto bookDto){

        Book book = bookService.addBook(Book.from(bookDto));
        return new ResponseEntity<>(BookDto.from(book), HttpStatus.OK);
    }

    /**
     * List all books
     * @return books represent object and HttpStatus
     */
    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks()
    {
        List<Book> books = bookService.getBooks();
        List<BookDto> booksDto = books.stream().map(BookDto::from).collect(Collectors.toList());

        return new ResponseEntity<>(booksDto,HttpStatus.OK);
    }


    /**
     * Change a book category
     * @param bookId    book id which will be changed
     * @param newCategoryId  another category id
     * @return
     */
    @PutMapping(value = "{bookId}/{newCategoryId}")
    public ResponseEntity<BookDto> changeBookCategory(@PathVariable final Long bookId,
                                                        @PathVariable final Long newCategoryId)
    {
        Book editedBook = categoryService.changeBookCategory(bookId, newCategoryId);
        return new ResponseEntity<>(BookDto.from(editedBook), HttpStatus.OK);

    }



}
