package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DistinctEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistinctEventDTO.class);
        DistinctEventDTO distinctEventDTO1 = new DistinctEventDTO();
        distinctEventDTO1.setId(1L);
        DistinctEventDTO distinctEventDTO2 = new DistinctEventDTO();
        assertThat(distinctEventDTO1).isNotEqualTo(distinctEventDTO2);
        distinctEventDTO2.setId(distinctEventDTO1.getId());
        assertThat(distinctEventDTO1).isEqualTo(distinctEventDTO2);
        distinctEventDTO2.setId(2L);
        assertThat(distinctEventDTO1).isNotEqualTo(distinctEventDTO2);
        distinctEventDTO1.setId(null);
        assertThat(distinctEventDTO1).isNotEqualTo(distinctEventDTO2);
    }
}
