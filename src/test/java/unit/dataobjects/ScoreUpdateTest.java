package unit.dataobjects;

import org.junit.jupiter.api.Test;

import dataobjects.data.ScoreUpdate;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScoreUpdateTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(ScoreUpdate.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(ScoreUpdate.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(ScoreUpdate.class, hasValidBeanEquals());
    }
}
