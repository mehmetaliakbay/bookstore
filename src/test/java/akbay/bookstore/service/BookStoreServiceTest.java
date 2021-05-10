package akbay.bookstore.service;

import akbay.bookstore.model.BookStore;
import akbay.bookstore.model.exception.BookStoreNotFoundException;
import akbay.bookstore.repository.BookStoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookStoreServiceTest {

    @InjectMocks
    private BookStoreService bookStoreService;

    @Mock
    private BookStoreRepository bookStoreRepository;

    @Mock
    private BookService bookService;

    @Test
    void testAddBookStore() {
        BookStore bookStore = new BookStore(1L,"testBookStoreName1",null);
        Mockito.when(bookStoreRepository.save(bookStore)).thenReturn(bookStore);
        assertEquals(bookStore,bookStoreService.addBookStore(bookStore));

    }
    @Test
    void testDuplicatedBookStoreException() {

        BookStore bookStore = new BookStore(1L, "testBookStoreName1", null);
        when(bookStoreRepository.save(bookStore)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class,() -> bookStoreService.addBookStore(bookStore));
    }

    @Test
    void testGetBookStore() {
        BookStore bookStore = new BookStore(1L, "testBookStoreName1", null);
        when(bookStoreRepository.findById(1L)).thenReturn(Optional.of(bookStore));
        assertEquals(bookStore,bookStoreService.getBookStore(1L));
    }

    @Test
    void testGetBookStores() {
        when(bookStoreRepository.findAll()).thenReturn(Stream.of(
                new BookStore(1L, "testBookStoreName1", null),
                new BookStore(2L, "testBookStoreName2", null)
        ).collect(Collectors.toList()));
        assertEquals(2,bookStoreService.getBookStores().size());

    }
    @Test
    void testBookNotFoundException()
    {
        Long id = 1L;
        assertThat(assertThrows(BookStoreNotFoundException.class,() -> bookStoreService.getBookStore(id)))
                .hasMessage("Could not find bookstore with id 1");

    }


    @Test
    void testAddBookToBookStore() {

        Long bookStoreId = 1L;
        Long bookId = 3L;
        assertThat(assertThrows(BookStoreNotFoundException.class,() -> bookStoreService.addBookToBookStore(bookStoreId,bookId)))
                .hasMessage("Could not find bookstore with id 1");


    }

    @Test
    void testRemoveBookFromBookStore() {
        Long bookStoreId = 1L;
        Long bookId = 3L;
        assertThat(assertThrows(BookStoreNotFoundException.class,() -> bookStoreService.removeBookFromBookStore(bookStoreId,bookId)))
                .hasMessage("Could not find bookstore with id 1");
    }

    @Test
    void testGetBooksAccordingToBookStore() {
        Long bookStoreId = 1L;
        assertThat(assertThrows(BookStoreNotFoundException.class,() -> bookStoreService.getBooksAccordingToBookStore(bookStoreId)))
                .hasMessage("Could not find bookstore with id 1");
    }
}