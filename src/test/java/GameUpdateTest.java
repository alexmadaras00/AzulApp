import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import dataobjects.GameUpdate;

public class GameUpdateTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(GameUpdate.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(GameUpdate.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(GameUpdate.class, hasValidBeanEquals());
    }
}
