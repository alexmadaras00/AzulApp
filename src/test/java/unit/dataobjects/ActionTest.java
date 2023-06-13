package unit.dataobjects;
import org.junit.jupiter.api.Test;

import messaging.dataobjects.Action;

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
