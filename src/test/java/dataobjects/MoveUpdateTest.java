package dataobjects;

import dataobjects.MoveUpdate;
import org.junit.jupiter.api.Test;
import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoveUpdateTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(MoveUpdate.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(MoveUpdate.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(MoveUpdate.class, hasValidBeanEquals());
    }
}
