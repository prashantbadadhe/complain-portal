package com.radixile.panvel.web.rest;

import com.radixile.panvel.ComplainportalApp;

import com.radixile.panvel.domain.Complain;
import com.radixile.panvel.repository.ComplainRepository;
import com.radixile.panvel.service.ComplainService;
import com.radixile.panvel.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.radixile.panvel.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ComplainResource REST controller.
 *
 * @see ComplainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComplainportalApp.class)
public class ComplainResourceIntTest {

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EXPECTION = "AAAAAAAAAA";
    private static final String UPDATED_EXPECTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW_ANONYMOUS = false;
    private static final Boolean UPDATED_SHOW_ANONYMOUS = true;

    private static final String DEFAULT_ESCALATE = "AAAAAAAAAA";
    private static final String UPDATED_ESCALATE = "BBBBBBBBBB";

    private static final String DEFAULT_RESOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION = "BBBBBBBBBB";

    @Autowired
    private ComplainRepository complainRepository;

    @Autowired
    private ComplainService complainService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComplainMockMvc;

    private Complain complain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComplainResource complainResource = new ComplainResource(complainService);
        this.restComplainMockMvc = MockMvcBuilders.standaloneSetup(complainResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Complain createEntity(EntityManager em) {
        Complain complain = new Complain()
            .details(DEFAULT_DETAILS)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .expection(DEFAULT_EXPECTION)
            .showAnonymous(DEFAULT_SHOW_ANONYMOUS)
            .escalate(DEFAULT_ESCALATE)
            .resolution(DEFAULT_RESOLUTION);
        return complain;
    }

    @Before
    public void initTest() {
        complain = createEntity(em);
    }

    @Test
    @Transactional
    public void createComplain() throws Exception {
        int databaseSizeBeforeCreate = complainRepository.findAll().size();

        // Create the Complain
        restComplainMockMvc.perform(post("/api/complains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complain)))
            .andExpect(status().isCreated());

        // Validate the Complain in the database
        List<Complain> complainList = complainRepository.findAll();
        assertThat(complainList).hasSize(databaseSizeBeforeCreate + 1);
        Complain testComplain = complainList.get(complainList.size() - 1);
        assertThat(testComplain.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testComplain.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testComplain.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testComplain.getExpection()).isEqualTo(DEFAULT_EXPECTION);
        assertThat(testComplain.isShowAnonymous()).isEqualTo(DEFAULT_SHOW_ANONYMOUS);
        assertThat(testComplain.getEscalate()).isEqualTo(DEFAULT_ESCALATE);
        assertThat(testComplain.getResolution()).isEqualTo(DEFAULT_RESOLUTION);
    }

    @Test
    @Transactional
    public void createComplainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = complainRepository.findAll().size();

        // Create the Complain with an existing ID
        complain.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplainMockMvc.perform(post("/api/complains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complain)))
            .andExpect(status().isBadRequest());

        // Validate the Complain in the database
        List<Complain> complainList = complainRepository.findAll();
        assertThat(complainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComplains() throws Exception {
        // Initialize the database
        complainRepository.saveAndFlush(complain);

        // Get all the complainList
        restComplainMockMvc.perform(get("/api/complains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complain.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].expection").value(hasItem(DEFAULT_EXPECTION.toString())))
            .andExpect(jsonPath("$.[*].showAnonymous").value(hasItem(DEFAULT_SHOW_ANONYMOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].escalate").value(hasItem(DEFAULT_ESCALATE.toString())))
            .andExpect(jsonPath("$.[*].resolution").value(hasItem(DEFAULT_RESOLUTION.toString())));
    }
    
    @Test
    @Transactional
    public void getComplain() throws Exception {
        // Initialize the database
        complainRepository.saveAndFlush(complain);

        // Get the complain
        restComplainMockMvc.perform(get("/api/complains/{id}", complain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(complain.getId().intValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.expection").value(DEFAULT_EXPECTION.toString()))
            .andExpect(jsonPath("$.showAnonymous").value(DEFAULT_SHOW_ANONYMOUS.booleanValue()))
            .andExpect(jsonPath("$.escalate").value(DEFAULT_ESCALATE.toString()))
            .andExpect(jsonPath("$.resolution").value(DEFAULT_RESOLUTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComplain() throws Exception {
        // Get the complain
        restComplainMockMvc.perform(get("/api/complains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComplain() throws Exception {
        // Initialize the database
        complainService.save(complain);

        int databaseSizeBeforeUpdate = complainRepository.findAll().size();

        // Update the complain
        Complain updatedComplain = complainRepository.findById(complain.getId()).get();
        // Disconnect from session so that the updates on updatedComplain are not directly saved in db
        em.detach(updatedComplain);
        updatedComplain
            .details(UPDATED_DETAILS)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .expection(UPDATED_EXPECTION)
            .showAnonymous(UPDATED_SHOW_ANONYMOUS)
            .escalate(UPDATED_ESCALATE)
            .resolution(UPDATED_RESOLUTION);

        restComplainMockMvc.perform(put("/api/complains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComplain)))
            .andExpect(status().isOk());

        // Validate the Complain in the database
        List<Complain> complainList = complainRepository.findAll();
        assertThat(complainList).hasSize(databaseSizeBeforeUpdate);
        Complain testComplain = complainList.get(complainList.size() - 1);
        assertThat(testComplain.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testComplain.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testComplain.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testComplain.getExpection()).isEqualTo(UPDATED_EXPECTION);
        assertThat(testComplain.isShowAnonymous()).isEqualTo(UPDATED_SHOW_ANONYMOUS);
        assertThat(testComplain.getEscalate()).isEqualTo(UPDATED_ESCALATE);
        assertThat(testComplain.getResolution()).isEqualTo(UPDATED_RESOLUTION);
    }

    @Test
    @Transactional
    public void updateNonExistingComplain() throws Exception {
        int databaseSizeBeforeUpdate = complainRepository.findAll().size();

        // Create the Complain

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplainMockMvc.perform(put("/api/complains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complain)))
            .andExpect(status().isBadRequest());

        // Validate the Complain in the database
        List<Complain> complainList = complainRepository.findAll();
        assertThat(complainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComplain() throws Exception {
        // Initialize the database
        complainService.save(complain);

        int databaseSizeBeforeDelete = complainRepository.findAll().size();

        // Get the complain
        restComplainMockMvc.perform(delete("/api/complains/{id}", complain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Complain> complainList = complainRepository.findAll();
        assertThat(complainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Complain.class);
        Complain complain1 = new Complain();
        complain1.setId(1L);
        Complain complain2 = new Complain();
        complain2.setId(complain1.getId());
        assertThat(complain1).isEqualTo(complain2);
        complain2.setId(2L);
        assertThat(complain1).isNotEqualTo(complain2);
        complain1.setId(null);
        assertThat(complain1).isNotEqualTo(complain2);
    }
}
