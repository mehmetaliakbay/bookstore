package akbay.bookstore.repository;

import akbay.bookstore.model.BookStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStoreRepository extends CrudRepository<BookStore,Long> {
}
