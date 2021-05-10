package akbay.bookstore.model.exception;

import java.text.MessageFormat;

public class BookIsAlreadyAddedException extends RuntimeException{
    public BookIsAlreadyAddedException(final Long bookId, final Long categoryId)
    {
        super(MessageFormat.format("Book: {0} is already added : {1}",bookId,categoryId));
    }
    public BookIsAlreadyAddedException(final Long bookId)
    {
        super(MessageFormat.format("Book: {0} is already added",bookId));
    }
}
