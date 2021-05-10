package akbay.bookstore.service;

import akbay.bookstore.model.Book;
import akbay.bookstore.model.BookStore;
import akbay.bookstore.model.exception.BookIsAlreadyAddedException;
import akbay.bookstore.model.exception.BookStoreNotFoundException;
import akbay.bookstore.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookStoreService {
    private BookStoreRepository bookStoreRepository;
    private BookService bookService;

    @Autowired
    public BookStoreService(BookStoreRepository bookStoreRepository, BookService bookService) {
        this.bookStoreRepository = bookStoreRepository;
        this.bookService = bookService;
    }




    public BookStore addBookStore(BookStore bookStore)
    {
        return bookStoreRepository.save(bookStore);
    }

    public BookStore getBookStore(Long id)
    {
        return bookStoreRepository.findById(id).orElseThrow(() ->
                new BookStoreNotFoundException(id));

    }

    public List<BookStore> getBookStores()
    {
        return StreamSupport
                .stream(bookStoreRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookStore addBookToBookStore(Long bookStoreId, Long bookId)
    {
        BookStore bookStore = getBookStore(bookStoreId);
        Book book = bookService.getBook(bookId);
        if(Objects.nonNull(book.getBookStore())){
            throw new BookIsAlreadyAddedException(bookId,book.getBookStore().getId());
        }
        bookStore.addBook(book);
        book.setBookStore(bookStore);
        return bookStore;

    }

    @Transactional
    public BookStore removeBookFromBookStore(Long bookStoreId, Long bookId)
    {
        BookStore bookStore = getBookStore(bookStoreId);
        Book book = bookService.getBook(bookId);
        bookStore.removeBook(book);
        return bookStore;
    }

    @Transactional
    public Set<Book> getBooksAccordingToBookStore(Long id)
    {
        BookStore bookStore  = getBookStore(id);
        return bookStore.getBooks();
    }
}
