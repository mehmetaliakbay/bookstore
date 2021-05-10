package akbay.bookstore.controller;

import akbay.bookstore.model.BookStore;
import akbay.bookstore.model.dto.BookDto;
import akbay.bookstore.model.dto.BookStoreDto;
import akbay.bookstore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookstores")
public class BookStoreController {
    private final BookStoreService bookStoreService;

    @Autowired
    public BookStoreController(BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    /**
     * Create a bookstore
     * @param bookStoreDto  bookstore data represent object
     * @return  return bookstore dto and HttpStatus
     */
    @PostMapping
    public ResponseEntity<BookStoreDto> addBookStore(@RequestBody final BookStoreDto bookStoreDto)
    {
        BookStore bookStore = bookStoreService.addBookStore(BookStore.from(bookStoreDto));
        bookStoreService.addBookStore(bookStore);
        return new ResponseEntity<>(BookStoreDto.from(bookStore), HttpStatus.OK);
    }

    /**
     * List all bookstores
     * @return  bookstore dto and HttpStatus
     */
    @GetMapping
    public ResponseEntity<List<BookStoreDto>> getBookStores()
    {
        List<BookStoreDto> bookStoreDtos = bookStoreService.getBookStores().stream().map(BookStoreDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(bookStoreDtos,HttpStatus.OK);
    }

    /**
     * Add a specific book to bookstore
     * @param bookStoreId   bookstore id
     * @param bookId    book id which will be added to bookstore
     * @return bookstore dto and HttpStatus
     */
    @PostMapping(value = "{bookStoreId}/books/{bookId}/add")
    public ResponseEntity<BookStoreDto> addBookToBookStore(@PathVariable final Long bookStoreId,
                                                           @PathVariable final Long bookId)
    {
        BookStore bookStore = bookStoreService.addBookToBookStore(bookStoreId,bookId);
        return new ResponseEntity<>(BookStoreDto.from(bookStore),HttpStatus.OK);

    }

    /**
     * Remove a specific book from bookstore
     * @param bookStoreId   bookstore id
     * @param bookId    book id which will be removed from bookstore
     * @return  bookstore dto and HttpStatus
     */
    @PostMapping(value = "{bookStoreId}/books/{bookId}/remove")
    public ResponseEntity<BookStoreDto> removeBookFromBookStore(@PathVariable final Long bookStoreId,
                                                           @PathVariable final Long bookId)
    {
        BookStore bookStore = bookStoreService.removeBookFromBookStore(bookStoreId,bookId);
        return new ResponseEntity<>(BookStoreDto.from(bookStore),HttpStatus.OK);

    }

    /**
     * List books according to specific bookstore
     * @param bookStoreId   bookstore id
     * @return  book dto list and HttpStatus
     */
    @GetMapping(value = "{bookStoreId}/books")
    public ResponseEntity<List<BookDto>> getBooksAccordingToBookStore(@PathVariable final Long bookStoreId)
    {
        List<BookDto> bookDtos = bookStoreService.getBooksAccordingToBookStore(bookStoreId).stream().map(BookDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(bookDtos,HttpStatus.OK);
    }
}
