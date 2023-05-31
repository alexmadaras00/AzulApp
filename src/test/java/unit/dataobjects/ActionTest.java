package unit.dataobjects;
import dataobjects.Action;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActionTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(Action.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(Action.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(Action.class, hasValidBeanEquals());
    }
}
