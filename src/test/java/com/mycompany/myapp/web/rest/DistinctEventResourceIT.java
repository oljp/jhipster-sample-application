package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.DistinctEvent;
import com.mycompany.myapp.repository.DistinctEventRepository;
import com.mycompany.myapp.service.DistinctEventService;
import com.mycompany.myapp.service.dto.DistinctEventDTO;
import com.mycompany.myapp.service.mapper.DistinctEventMapper;
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
 * Integration tests for the {@link DistinctEventResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DistinctEventResourceIT {

    private static final String DEFAULT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_DATASET = "AAAAAAAAAA";
    private static final String UPDATED_DATASET = "BBBBBBBBBB";

    @Autowired
    private DistinctEventRepository distinctEventRepository;

    @Mock
    private DistinctEventRepository distinctEventRepositoryMock;

    @Autowired
    private DistinctEventMapper distinctEventMapper;

    @Mock
    private DistinctEventService distinctEventServiceMock;

    @Autowired
    private DistinctEventService distinctEventService;

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

    private MockMvc restDistinctEventMockMvc;

    private DistinctEvent distinctEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DistinctEventResource distinctEventResource = new DistinctEventResource(distinctEventService);
        this.restDistinctEventMockMvc = MockMvcBuilders.standaloneSetup(distinctEventResource)
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
    public static DistinctEvent createEntity(EntityManager em) {
        DistinctEvent distinctEvent = new DistinctEvent()
            .provider(DEFAULT_PROVIDER)
            .dataset(DEFAULT_DATASET);
        return distinctEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistinctEvent createUpdatedEntity(EntityManager em) {
        DistinctEvent distinctEvent = new DistinctEvent()
            .provider(UPDATED_PROVIDER)
            .dataset(UPDATED_DATASET);
        return distinctEvent;
    }

    @BeforeEach
    public void initTest() {
        distinctEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistinctEvent() throws Exception {
        int databaseSizeBeforeCreate = distinctEventRepository.findAll().size();

        // Create the DistinctEvent
        DistinctEventDTO distinctEventDTO = distinctEventMapper.toDto(distinctEvent);
        restDistinctEventMockMvc.perform(post("/api/distinct-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(distinctEventDTO)))
            .andExpect(status().isCreated());

        // Validate the DistinctEvent in the database
        List<DistinctEvent> distinctEventList = distinctEventRepository.findAll();
        assertThat(distinctEventList).hasSize(databaseSizeBeforeCreate + 1);
        DistinctEvent testDistinctEvent = distinctEventList.get(distinctEventList.size() - 1);
        assertThat(testDistinctEvent.getProvider()).isEqualTo(DEFAULT_PROVIDER);
        assertThat(testDistinctEvent.getDataset()).isEqualTo(DEFAULT_DATASET);
    }

    @Test
    @Transactional
    public void createDistinctEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = distinctEventRepository.findAll().size();

        // Create the DistinctEvent with an existing ID
        distinctEvent.setId(1L);
        DistinctEventDTO distinctEventDTO = distinctEventMapper.toDto(distinctEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistinctEventMockMvc.perform(post("/api/distinct-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(distinctEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DistinctEvent in the database
        List<DistinctEvent> distinctEventList = distinctEventRepository.findAll();
        assertThat(distinctEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDistinctEvents() throws Exception {
        // Initialize the database
        distinctEventRepository.saveAndFlush(distinctEvent);

        // Get all the distinctEventList
        restDistinctEventMockMvc.perform(get("/api/distinct-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distinctEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER)))
            .andExpect(jsonPath("$.[*].dataset").value(hasItem(DEFAULT_DATASET)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDistinctEventsWithEagerRelationshipsIsEnabled() throws Exception {
        DistinctEventResource distinctEventResource = new DistinctEventResource(distinctEventServiceMock);
        when(distinctEventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDistinctEventMockMvc = MockMvcBuilders.standaloneSetup(distinctEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDistinctEventMockMvc.perform(get("/api/distinct-events?eagerload=true"))
        .andExpect(status().isOk());

        verify(distinctEventServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDistinctEventsWithEagerRelationshipsIsNotEnabled() throws Exception {
        DistinctEventResource distinctEventResource = new DistinctEventResource(distinctEventServiceMock);
            when(distinctEventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDistinctEventMockMvc = MockMvcBuilders.standaloneSetup(distinctEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDistinctEventMockMvc.perform(get("/api/distinct-events?eagerload=true"))
        .andExpect(status().isOk());

            verify(distinctEventServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDistinctEvent() throws Exception {
        // Initialize the database
        distinctEventRepository.saveAndFlush(distinctEvent);

        // Get the distinctEvent
        restDistinctEventMockMvc.perform(get("/api/distinct-events/{id}", distinctEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(distinctEvent.getId().intValue()))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER))
            .andExpect(jsonPath("$.dataset").value(DEFAULT_DATASET));
    }

    @Test
    @Transactional
    public void getNonExistingDistinctEvent() throws Exception {
        // Get the distinctEvent
        restDistinctEventMockMvc.perform(get("/api/distinct-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistinctEvent() throws Exception {
        // Initialize the database
        distinctEventRepository.saveAndFlush(distinctEvent);

        int databaseSizeBeforeUpdate = distinctEventRepository.findAll().size();

        // Update the distinctEvent
        DistinctEvent updatedDistinctEvent = distinctEventRepository.findById(distinctEvent.getId()).get();
        // Disconnect from session so that the updates on updatedDistinctEvent are not directly saved in db
        em.detach(updatedDistinctEvent);
        updatedDistinctEvent
            .provider(UPDATED_PROVIDER)
            .dataset(UPDATED_DATASET);
        DistinctEventDTO distinctEventDTO = distinctEventMapper.toDto(updatedDistinctEvent);

        restDistinctEventMockMvc.perform(put("/api/distinct-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(distinctEventDTO)))
            .andExpect(status().isOk());

        // Validate the DistinctEvent in the database
        List<DistinctEvent> distinctEventList = distinctEventRepository.findAll();
        assertThat(distinctEventList).hasSize(databaseSizeBeforeUpdate);
        DistinctEvent testDistinctEvent = distinctEventList.get(distinctEventList.size() - 1);
        assertThat(testDistinctEvent.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testDistinctEvent.getDataset()).isEqualTo(UPDATED_DATASET);
    }

    @Test
    @Transactional
    public void updateNonExistingDistinctEvent() throws Exception {
        int databaseSizeBeforeUpdate = distinctEventRepository.findAll().size();

        // Create the DistinctEvent
        DistinctEventDTO distinctEventDTO = distinctEventMapper.toDto(distinctEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistinctEventMockMvc.perform(put("/api/distinct-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(distinctEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DistinctEvent in the database
        List<DistinctEvent> distinctEventList = distinctEventRepository.findAll();
        assertThat(distinctEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDistinctEvent() throws Exception {
        // Initialize the database
        distinctEventRepository.saveAndFlush(distinctEvent);

        int databaseSizeBeforeDelete = distinctEventRepository.findAll().size();

        // Delete the distinctEvent
        restDistinctEventMockMvc.perform(delete("/api/distinct-events/{id}", distinctEvent.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DistinctEvent> distinctEventList = distinctEventRepository.findAll();
        assertThat(distinctEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
