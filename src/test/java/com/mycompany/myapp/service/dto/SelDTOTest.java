package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelDTO.class);
        SelDTO selDTO1 = new SelDTO();
        selDTO1.setId(1L);
        SelDTO selDTO2 = new SelDTO();
        assertThat(selDTO1).isNotEqualTo(selDTO2);
        selDTO2.setId(selDTO1.getId());
        assertThat(selDTO1).isEqualTo(selDTO2);
        selDTO2.setId(2L);
        assertThat(selDTO1).isNotEqualTo(selDTO2);
        selDTO1.setId(null);
        assertThat(selDTO1).isNotEqualTo(selDTO2);
    }
}
