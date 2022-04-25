package com.epam.esm.api.integration;

import com.epam.esm.api.exceptionhandler.GlobalExceptionHandler;
import com.epam.esm.api.integration.config.IntegrationTestConfig;
import com.epam.esm.core.configuration.WebInitializer;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.SearchParamsDto;
import com.epam.esm.core.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static com.epam.esm.api.integration.util.JsonUtils.toJson;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {IntegrationTestConfig.class, WebInitializer.class})
@WebAppConfiguration
@Sql(scripts = {"classpath:scripts/init_gift_certificate.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class GiftCertificateIntegrationTest {
    public static final String CERTIFICATES_ENDPOINT = "/api/gift-certificates";

    private final WebApplicationContext webAppContext;
    private MockMvc mvc;
    private GiftCertificateDto giftCertificateDto;

    @Autowired
    public GiftCertificateIntegrationTest(WebApplicationContext webAppContext) {
        this.webAppContext = webAppContext;
    }

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webAppContext).build();
    }

    @BeforeEach
    public void init() {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(2, "candy"));
        tagSet.add(new Tag(1, "food"));
        giftCertificateDto = GiftCertificateDto.builder()
                .id(1)
                .name("Unlimited candy supply")
                .description("This certificate provides unlimited candy supply to the owner and owner only!")
                .duration(3)
                .price(50)
                .createDate("2018-08-29T06:12:12")
                .lastUpdateDate("2018-08-29T06:12:12")
                .tagSet(tagSet)
                .build();
    }

    @Test
    public void shouldExistsGlobalControllerAdvice() {
        GlobalExceptionHandler handler = this.webAppContext.getBean(GlobalExceptionHandler.class);
        assertNotNull(handler);
    }

    @Test
    public void shouldReturnJsonIfGetRequest() throws Exception {
        mvc.perform(get(CERTIFICATES_ENDPOINT))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCorrectJsonIfGetRequestById() throws Exception {
        mvc.perform(get(CERTIFICATES_ENDPOINT + "/{id}", String.valueOf(giftCertificateDto.getId())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(giftCertificateDto.getId()))
                .andExpect(jsonPath("$.name").value(giftCertificateDto.getName()))
                .andExpect(jsonPath("$.description").value(giftCertificateDto.getDescription()))
                .andExpect(jsonPath("$.duration").value(giftCertificateDto.getDuration()))
                .andExpect(jsonPath("$.price").value(giftCertificateDto.getPrice()))
                .andExpect(jsonPath("$.createDate").value(giftCertificateDto.getCreateDate()))
                .andExpect(jsonPath("$.lastUpdateDate").value(giftCertificateDto.getLastUpdateDate()));
    }

    @Test
    public void shouldReturnBadRequestIfGetRequestByIdWithWrongId() throws Exception {
        mvc.perform(get(CERTIFICATES_ENDPOINT + "/{id}", "abc"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundIfGetRequestByIdWithNotExistingId() throws Exception {
        mvc.perform(get(CERTIFICATES_ENDPOINT + "/{id}", "9999"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCorrectJsonIfPostWithValidBody() throws Exception {
        mvc.perform(post(CERTIFICATES_ENDPOINT).content(toJson(giftCertificateDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(giftCertificateDto.getId() + 1))
                .andExpect(jsonPath("$.name").value(giftCertificateDto.getName()))
                .andExpect(jsonPath("$.description").value(giftCertificateDto.getDescription()))
                .andExpect(jsonPath("$.duration").value(giftCertificateDto.getDuration()))
                .andExpect(jsonPath("$.price").value(giftCertificateDto.getPrice()))
                .andExpect(jsonPath("$.createDate").value(giftCertificateDto.getCreateDate()))
                .andExpect(jsonPath("$.lastUpdateDate").value(giftCertificateDto.getLastUpdateDate()))
                .andExpect(jsonPath("$.tagSet").isNotEmpty());
    }

    @Test
    public void shouldReturnBadRequestIfPostWithInvalidBody() throws Exception {
        mvc.perform(post(CERTIFICATES_ENDPOINT).content("").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfPutWithInvalidBody() throws Exception {
        giftCertificateDto.setName("");
        mvc.perform(put(CERTIFICATES_ENDPOINT).content(toJson(giftCertificateDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCorrectJsonIfPutWithValidBody() throws Exception {
        mvc.perform(put(CERTIFICATES_ENDPOINT).content(toJson(giftCertificateDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(giftCertificateDto.getId()))
                .andExpect(jsonPath("$.name").value(giftCertificateDto.getName()))
                .andExpect(jsonPath("$.description").value(giftCertificateDto.getDescription()))
                .andExpect(jsonPath("$.duration").value(giftCertificateDto.getDuration()))
                .andExpect(jsonPath("$.price").value(giftCertificateDto.getPrice()))
                .andExpect(jsonPath("$.createDate").value(giftCertificateDto.getCreateDate()))
                .andExpect(jsonPath("$.lastUpdateDate").value(giftCertificateDto.getLastUpdateDate()))
                .andExpect(jsonPath("$.tagSet").isNotEmpty());
    }

    @Test
    public void shouldReturnNoContentIfDelete() throws Exception {
        mvc.perform(delete(CERTIFICATES_ENDPOINT + "/{id}", String.valueOf(giftCertificateDto.getId())))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestIfDeleteWithWrongId() throws Exception {
        mvc.perform(delete(CERTIFICATES_ENDPOINT + "/{id}", "abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfPostWithWrongBody() throws Exception {
        giftCertificateDto.setName("");
        mvc.perform(post(CERTIFICATES_ENDPOINT).content(toJson(giftCertificateDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfSearchWithWrongBody() throws Exception {
        SearchParamsDto searchParamsDto = new SearchParamsDto();
        searchParamsDto.setSortType("abc");
        mvc.perform(get(CERTIFICATES_ENDPOINT + "/search").content(toJson(searchParamsDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCorrectJsonIfSearchWithCorrectBody() throws Exception {
        SearchParamsDto searchParamsDto = new SearchParamsDto();
        searchParamsDto.setName("candy");
        mvc.perform(get(CERTIFICATES_ENDPOINT + "/search").content(toJson(searchParamsDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
//                .andExpect(jsonPath("$.[0].id").value(giftCertificateDto.getId()))
//                .andExpect(jsonPath("$.[0].name").value(giftCertificateDto.getName()))
//                .andExpect(jsonPath("$.[0].description").value(giftCertificateDto.getDescription()))
//                .andExpect(jsonPath("$.[0].duration").value(giftCertificateDto.getDuration()))
//                .andExpect(jsonPath("$.[0].price").value(giftCertificateDto.getPrice()))
//                .andExpect(jsonPath("$.[0].createDate").value(giftCertificateDto.getCreateDate()))
//                .andExpect(jsonPath("$.[0].lastUpdateDate").value(giftCertificateDto.getLastUpdateDate()))
//                .andExpect(jsonPath("$.[0].tagSet").isNotEmpty());
    }
}