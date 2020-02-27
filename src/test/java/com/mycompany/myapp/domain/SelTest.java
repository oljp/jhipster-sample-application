package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sel.class);
        Sel sel1 = new Sel();
        sel1.setId(1L);
        Sel sel2 = new Sel();
        sel2.setId(sel1.getId());
        assertThat(sel1).isEqualTo(sel2);
        sel2.setId(2L);
        assertThat(sel1).isNotEqualTo(sel2);
        sel1.setId(null);
        assertThat(sel1).isNotEqualTo(sel2);
    }
}
