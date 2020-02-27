package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Sel;
import com.mycompany.myapp.repository.SelRepository;
import com.mycompany.myapp.service.SelService;
import com.mycompany.myapp.service.dto.SelDTO;
import com.mycompany.myapp.service.mapper.SelMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SelResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class SelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RHEL = "AAAAAAAAAA";
    private static final String UPDATED_RHEL = "BBBBBBBBBB";

    @Autowired
    private SelRepository selRepository;

    @Mock
    private SelRepository selRepositoryMock;

    @Autowired
    private SelMapper selMapper;

    @Mock
    private SelService selServiceMock;

    @Autowired
    private SelService selService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSelMockMvc;

    private Sel sel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SelResource selResource = new SelResource(selService);
        this.restSelMockMvc = MockMvcBuilders.standaloneSetup(selResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sel createEntity(EntityManager em) {
        Sel sel = new Sel()
            .name(DEFAULT_NAME)
            .rhel(DEFAULT_RHEL);
        return sel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sel createUpdatedEntity(EntityManager em) {
        Sel sel = new Sel()
            .name(UPDATED_NAME)
            .rhel(UPDATED_RHEL);
        return sel;
    }

    @BeforeEach
    public void initTest() {
        sel = createEntity(em);
    }

    @Test
    @Transactional
    public void createSel() throws Exception {
        int databaseSizeBeforeCreate = selRepository.findAll().size();

        // Create the Sel
        SelDTO selDTO = selMapper.toDto(sel);
        restSelMockMvc.perform(post("/api/sels")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(selDTO)))
            .andExpect(status().isCreated());

        // Validate the Sel in the database
        List<Sel> selList = selRepository.findAll();
        assertThat(selList).hasSize(databaseSizeBeforeCreate + 1);
        Sel testSel = selList.get(selList.size() - 1);
        assertThat(testSel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSel.getRhel()).isEqualTo(DEFAULT_RHEL);
    }

    @Test
    @Transactional
    public void createSelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = selRepository.findAll().size();

        // Create the Sel with an existing ID
        sel.setId(1L);
        SelDTO selDTO = selMapper.toDto(sel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelMockMvc.perform(post("/api/sels")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(selDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sel in the database
        List<Sel> selList = selRepository.findAll();
        assertThat(selList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSels() throws Exception {
        // Initialize the database
        selRepository.saveAndFlush(sel);

        // Get all the selList
        restSelMockMvc.perform(get("/api/sels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rhel").value(hasItem(DEFAULT_RHEL)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSelsWithEagerRelationshipsIsEnabled() throws Exception {
        SelResource selResource = new SelResource(selServiceMock);
        when(selServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restSelMockMvc = MockMvcBuilders.standaloneSetup(selResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSelMockMvc.perform(get("/api/sels?eagerload=true"))
        .andExpect(status().isOk());

        verify(selServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        SelResource selResource = new SelResource(selServiceMock);
            when(selServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restSelMockMvc = MockMvcBuilders.standaloneSetup(selResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSelMockMvc.perform(get("/api/sels?eagerload=true"))
        .andExpect(status().isOk());

            verify(selServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSel() throws Exception {
        // Initialize the database
        selRepository.saveAndFlush(sel);

        // Get the sel
        restSelMockMvc.perform(get("/api/sels/{id}", sel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.rhel").value(DEFAULT_RHEL));
    }

    @Test
    @Transactional
    public void getNonExistingSel() throws Exception {
        // Get the sel
        restSelMockMvc.perform(get("/api/sels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSel() throws Exception {
        // Initialize the database
        selRepository.saveAndFlush(sel);

        int databaseSizeBeforeUpdate = selRepository.findAll().size();

        // Update the sel
        Sel updatedSel = selRepository.findById(sel.getId()).get();
        // Disconnect from session so that the updates on updatedSel are not directly saved in db
        em.detach(updatedSel);
        updatedSel
            .name(UPDATED_NAME)
            .rhel(UPDATED_RHEL);
        SelDTO selDTO = selMapper.toDto(updatedSel);

        restSelMockMvc.perform(put("/api/sels")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(selDTO)))
            .andExpect(status().isOk());

        // Validate the Sel in the database
        List<Sel> selList = selRepository.findAll();
        assertThat(selList).hasSize(databaseSizeBeforeUpdate);
        Sel testSel = selList.get(selList.size() - 1);
        assertThat(testSel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSel.getRhel()).isEqualTo(UPDATED_RHEL);
    }

    @Test
    @Transactional
    public void updateNonExistingSel() throws Exception {
        int databaseSizeBeforeUpdate = selRepository.findAll().size();

        // Create the Sel
        SelDTO selDTO = selMapper.toDto(sel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSelMockMvc.perform(put("/api/sels")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(selDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sel in the database
        List<Sel> selList = selRepository.findAll();
        assertThat(selList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSel() throws Exception {
        // Initialize the database
        selRepository.saveAndFlush(sel);

        int databaseSizeBeforeDelete = selRepository.findAll().size();

        // Delete the sel
        restSelMockMvc.perform(delete("/api/sels/{id}", sel.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sel> selList = selRepository.findAll();
        assertThat(selList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
