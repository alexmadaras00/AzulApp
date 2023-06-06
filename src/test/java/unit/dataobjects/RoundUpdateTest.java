package unit.dataobjects;

import dataobjects.RoundUpdate;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class RoundUpdateTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(RoundUpdate.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(RoundUpdate.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(RoundUpdate.class, hasValidBeanEquals());
    }
}
