package akbay.bookstore.service;

import akbay.bookstore.model.Book;
import akbay.bookstore.model.exception.BookNotFoundException;
import akbay.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Book addBook(Book book)
    {
        return bookRepository.save(book);
    }

    public List<Book> getBooks(){
        return StreamSupport
                .stream(bookRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    public Book getBook(Long id)
    {
        return bookRepository.findById(id).orElseThrow(()->
                new BookNotFoundException(id));
    }









}
