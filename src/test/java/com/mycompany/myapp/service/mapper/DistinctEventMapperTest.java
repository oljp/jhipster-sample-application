package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DistinctEventMapperTest {

    private DistinctEventMapper distinctEventMapper;

    @BeforeEach
    public void setUp() {
        distinctEventMapper = new DistinctEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(distinctEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(distinctEventMapper.fromId(null)).isNull();
    }
}
