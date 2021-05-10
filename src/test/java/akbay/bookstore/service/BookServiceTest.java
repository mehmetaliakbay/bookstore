package akbay.bookstore.service;

import akbay.bookstore.model.Book;
import akbay.bookstore.model.exception.BookNotFoundException;
import akbay.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;

    @Test
    void testAddBook() {

        Book book = new Book(1L, "testBookName1", BigDecimal.TEN, null, null);
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book,bookService.addBook(book));

    }

    @Test
    void duplicatedBookException() {

        Book book = new Book(1L, "testBookName1", BigDecimal.TEN, null, null);
        when(bookRepository.save(book)).thenThrow(DataIntegrityViolationException.class);
        assertThat(assertThrows(DataIntegrityViolationException.class,() -> bookService.addBook(book)));

    }
    @Test
    void testGetBooks() {

        when(bookRepository.findAll()).thenReturn(Stream.of(
                new Book(1L, "testBookName1", BigDecimal.TEN, null, null),
                new Book(2L, "testBookName2", BigDecimal.ONE, null, null)
        ).collect(Collectors.toList()));
        assertEquals(2,bookService.getBooks().size());
    }

    @Test
    void testGetBook() {
        Book book = new Book(1L, "testBookName1", BigDecimal.TEN, null, null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertEquals(book,bookService.getBook(1L));
    }

    @Test
    void testBookNotFoundException()
    {
        Long id = 1L;
        assertThat(assertThrows(BookNotFoundException.class,() -> bookService.getBook(id)))
                .hasMessage("Could not find book with id: 1");

    }


}