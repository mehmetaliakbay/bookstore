package akbay.bookstore.model.exception;

import java.text.MessageFormat;

public class BookStoreNotFoundException extends RuntimeException{
    public BookStoreNotFoundException(final Long id)
    {
        super(MessageFormat.format("Could not find bookstore with id {0}",id));
    }
}
