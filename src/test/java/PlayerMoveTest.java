import dataobjects.PlayerMove;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class PlayerMoveTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(PlayerMove.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(PlayerMove.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(PlayerMove.class, hasValidBeanEquals());
    }
}
