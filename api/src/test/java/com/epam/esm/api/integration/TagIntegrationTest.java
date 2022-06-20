package com.epam.esm.api.integration;

import com.epam.esm.api.Application;
import com.epam.esm.api.integration.config.IntegrationTestConfig;
import com.epam.esm.core.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.esm.api.integration.util.JsonUtils.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:scripts/init_tag.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(
        classes = Application.class)
@Import(IntegrationTestConfig.class)
public class TagIntegrationTest {
    public static final String TAGS_ENDPOINT = "/api/v1/tags";

    private final WebApplicationContext webAppContext;
    private MockMvc mvc;
    private TagDto tag;

    public TagIntegrationTest(WebApplicationContext webAppContext) {
        this.webAppContext = webAppContext;
    }

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webAppContext).build();
    }

    @BeforeEach
    public void init() {
        tag = TagDto.builder()
                .id(1)
                .name("candy")
                .build();
    }

    @Test
    public void shouldReturnJsonIfGetRequest() throws Exception {
        mvc.perform(get(TAGS_ENDPOINT + "?page=1&size=1000"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCorrectJsonIfGetRequestById() throws Exception {
        mvc.perform(get(TAGS_ENDPOINT + "/{id}", String.valueOf(tag.getId())))
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(tag.getId()))
                .andExpect(jsonPath("$.name").value(tag.getName()));
    }

    @Test
    public void shouldReturnNotFoundIfGetRequestByIdWithNotExistingId() throws Exception {
        mvc.perform(get(TAGS_ENDPOINT + "/{id}", "9999"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnJsonIfPostRequest() throws Exception {
        tag.setName("food");
        mvc.perform(post(TAGS_ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(toJson(tag)))
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(tag.getId() + 1))
                .andExpect(jsonPath("$.name").value(tag.getName()));
    }

    @Test
    public void shouldReturnConflictIfTagWithSameNameExists() throws Exception {
        mvc.perform(post(TAGS_ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(toJson(tag)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnNoContentIfDelete() throws Exception {
        mvc.perform(delete(TAGS_ENDPOINT + "/{id}", String.valueOf(tag.getId())))
                .andExpect(status().isNoContent());
    }
}
