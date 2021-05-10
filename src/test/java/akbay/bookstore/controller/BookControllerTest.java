package akbay.bookstore.controller;

import akbay.bookstore.model.dto.BookDto;
import akbay.bookstore.service.BookService;
import akbay.bookstore.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {


    @MockBean
    private BookController bookController;

    @MockBean
    private BookService bookService;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenAddingBook_thenResult200() throws Exception {

        //given
        BookDto book = BookDto.builder().name("effective java").price(BigDecimal.TEN).build();


        //when
        ResultActions actions = mockMvc.perform(
                post("/books")
                        .content(objectMapper.writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        ArgumentCaptor<BookDto> captor = ArgumentCaptor.forClass(BookDto.class);
        verify(bookController, times(1)).addBook(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("effective java");
        assertThat(captor.getValue().getPrice()).isEqualTo(BigDecimal.TEN);
        actions.andExpect(status().isOk());

    }


    @Test
    void whenInvalidInputForAddingBook_thenReturns400() throws Exception {

        //given
        BookDto book = new BookDto();
        book.setPrice(BigDecimal.TEN);


        //when
        ResultActions actions = mockMvc.perform(
                post("/books")
                        .content(objectMapper.writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isBadRequest());

    }

    @Test
    void whenGetAllBooks_thenReturnsNoData() throws Exception {

        //when
        MvcResult mvcResult = mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();


        //then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(bookController,times(1)).getBooks();
        assertThat(responseBody).isEmpty();

    }


}