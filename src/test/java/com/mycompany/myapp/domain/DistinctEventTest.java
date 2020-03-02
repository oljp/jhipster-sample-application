package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DistinctEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistinctEvent.class);
        DistinctEvent distinctEvent1 = new DistinctEvent();
        distinctEvent1.setId(1L);
        DistinctEvent distinctEvent2 = new DistinctEvent();
        distinctEvent2.setId(distinctEvent1.getId());
        assertThat(distinctEvent1).isEqualTo(distinctEvent2);
        distinctEvent2.setId(2L);
        assertThat(distinctEvent1).isNotEqualTo(distinctEvent2);
        distinctEvent1.setId(null);
        assertThat(distinctEvent1).isNotEqualTo(distinctEvent2);
    }
}
