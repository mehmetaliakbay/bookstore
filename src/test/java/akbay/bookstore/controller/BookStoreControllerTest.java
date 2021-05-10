package akbay.bookstore.controller;

import akbay.bookstore.model.dto.BookStoreDto;
import akbay.bookstore.service.BookStoreService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookStoreController.class)
class BookStoreControllerTest {

    @MockBean
    private BookStoreController bookStoreController;

    @MockBean
    private BookStoreService bookStoreService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenAddingBookStore_thenResult200() throws Exception {

        //given
        BookStoreDto bookStoreDto = BookStoreDto.builder().name("Istanbul Book Store").build();


        //when
        ResultActions actions = mockMvc.perform(
                post("/bookstores")
                        .content(objectMapper.writeValueAsString(bookStoreDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        ArgumentCaptor<BookStoreDto> captor = ArgumentCaptor.forClass(BookStoreDto.class);
        verify(bookStoreController, times(1)).addBookStore(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Istanbul Book Store");
        actions.andExpect(status().isOk());

    }

    @Test
    void whenInvalidInputForAddingBookStore_thenReturns400() throws Exception {

        //given
        BookStoreDto bookStoreDto = new BookStoreDto();
        bookStoreDto.setId(1L);


        //when
        ResultActions actions = mockMvc.perform(
                post("/bookstores")
                        .content(objectMapper.writeValueAsString(bookStoreDto))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isBadRequest());

    }

    @Test
    void whenGetAllBookStores_thenReturnsNoData() throws Exception {

        //when
        MvcResult mvcResult = mockMvc.perform(get("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();


        //then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(bookStoreController,times(1)).getBookStores();
        assertThat(responseBody).isEmpty();

    }


}