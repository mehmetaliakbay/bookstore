package akbay.bookstore.service;

import akbay.bookstore.model.Book;
import akbay.bookstore.model.Category;
import akbay.bookstore.model.exception.BookIsAlreadyAddedException;
import akbay.bookstore.model.exception.CategoryNotFoundException;
import akbay.bookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookService bookService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, BookService bookService) {
        this.categoryRepository = categoryRepository;
        this.bookService = bookService;
    }


    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getCategories() {
        return StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException(id));
    }


    @Transactional
    public Category addBookToCategory(Long categoryId, Long bookId) {
        Category category = getCategory(categoryId);
        Book book = bookService.getBook(bookId);
        if (Objects.nonNull(book.getCategory())) {
            throw new BookIsAlreadyAddedException(bookId, book.getCategory().getId());
        }
        category.addBook(book);
        book.setCategory(category);
        return category;
    }

    @Transactional
    public Category removeBookFromCategory(Long categoryId, Long bookId) {
        Category category = getCategory(categoryId);
        Book book = bookService.getBook(bookId);
        category.removeBook(book);
        return category;
    }


    @Transactional
    public Set<Book> getBooksAccordingToCategory(Long id) {
        Category category = getCategory(id);
        return category.getBooks();
    }

    @Transactional
    public Book changeBookCategory(Long bookId, Long categoryId) {
        Book bookToEdit = bookService.getBook(bookId);
        Category category = getCategory(categoryId);
        bookToEdit.setCategory(category);
        return bookToEdit;
    }
}
