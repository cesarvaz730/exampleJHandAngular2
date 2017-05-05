package com.mx.anivip.web.rest;

import com.mx.anivip.AnivipTestApp;

import com.mx.anivip.domain.Pass;
import com.mx.anivip.repository.PassRepository;
import com.mx.anivip.service.PassService;
import com.mx.anivip.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PassResource REST controller.
 *
 * @see PassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnivipTestApp.class)
public class PassResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private PassRepository passRepository;

    @Autowired
    private PassService passService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPassMockMvc;

    private Pass pass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PassResource passResource = new PassResource(passService);
        this.restPassMockMvc = MockMvcBuilders.standaloneSetup(passResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pass createEntity() {
        Pass pass = new Pass()
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE);
        return pass;
    }

    @Before
    public void initTest() {
        passRepository.deleteAll();
        pass = createEntity();
    }

    @Test
    public void createPass() throws Exception {
        int databaseSizeBeforeCreate = passRepository.findAll().size();

        // Create the Pass
        restPassMockMvc.perform(post("/api/passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pass)))
            .andExpect(status().isCreated());

        // Validate the Pass in the database
        List<Pass> passList = passRepository.findAll();
        assertThat(passList).hasSize(databaseSizeBeforeCreate + 1);
        Pass testPass = passList.get(passList.size() - 1);
        assertThat(testPass.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPass.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    public void createPassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = passRepository.findAll().size();

        // Create the Pass with an existing ID
        pass.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassMockMvc.perform(post("/api/passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pass)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pass> passList = passRepository.findAll();
        assertThat(passList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPasses() throws Exception {
        // Initialize the database
        passRepository.save(pass);

        // Get all the passList
        restPassMockMvc.perform(get("/api/passes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pass.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    public void getPass() throws Exception {
        // Initialize the database
        passRepository.save(pass);

        // Get the pass
        restPassMockMvc.perform(get("/api/passes/{id}", pass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pass.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    public void getNonExistingPass() throws Exception {
        // Get the pass
        restPassMockMvc.perform(get("/api/passes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePass() throws Exception {
        // Initialize the database
        passService.save(pass);

        int databaseSizeBeforeUpdate = passRepository.findAll().size();

        // Update the pass
        Pass updatedPass = passRepository.findOne(pass.getId());
        updatedPass
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);

        restPassMockMvc.perform(put("/api/passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPass)))
            .andExpect(status().isOk());

        // Validate the Pass in the database
        List<Pass> passList = passRepository.findAll();
        assertThat(passList).hasSize(databaseSizeBeforeUpdate);
        Pass testPass = passList.get(passList.size() - 1);
        assertThat(testPass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPass.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    public void updateNonExistingPass() throws Exception {
        int databaseSizeBeforeUpdate = passRepository.findAll().size();

        // Create the Pass

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPassMockMvc.perform(put("/api/passes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pass)))
            .andExpect(status().isCreated());

        // Validate the Pass in the database
        List<Pass> passList = passRepository.findAll();
        assertThat(passList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePass() throws Exception {
        // Initialize the database
        passService.save(pass);

        int databaseSizeBeforeDelete = passRepository.findAll().size();

        // Get the pass
        restPassMockMvc.perform(delete("/api/passes/{id}", pass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pass> passList = passRepository.findAll();
        assertThat(passList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pass.class);
    }
}
