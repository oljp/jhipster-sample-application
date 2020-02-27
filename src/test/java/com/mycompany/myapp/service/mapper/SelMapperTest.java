package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SelMapperTest {

    private SelMapper selMapper;

    @BeforeEach
    public void setUp() {
        selMapper = new SelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(selMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(selMapper.fromId(null)).isNull();
    }
}
