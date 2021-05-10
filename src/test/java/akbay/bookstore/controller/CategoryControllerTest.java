package akbay.bookstore.controller;

import akbay.bookstore.model.dto.CategoryDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @MockBean
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenAddingCategory_thenResult200() throws Exception {

        //given
        CategoryDto categoryDto = CategoryDto.builder().name("programming").build();


        //when
        ResultActions actions = mockMvc.perform(
                post("/categories")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        ArgumentCaptor<CategoryDto> captor = ArgumentCaptor.forClass(CategoryDto.class);
        verify(categoryController,times(1)).addCategory(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("programming");
        actions.andExpect(status().isOk());

    }

    @Test
    void whenInvalidInputForAddingCategory_thenReturns400() throws Exception {

        //given
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);


        //when
        ResultActions actions = mockMvc.perform(
                post("/categories")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isBadRequest());

    }

    @Test
    void whenGetAllCategories_thenReturnsNoData() throws Exception {

        //when
        MvcResult mvcResult = mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();


        //then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(categoryController,times(1)).getCategories();
        assertThat(responseBody).isEmpty();

    }

}