import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;

import dataobjects.ScoreChange;

public class ScoreChangeTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(ScoreChange.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(ScoreChange.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(ScoreChange.class, hasValidBeanEquals());
    }
}
