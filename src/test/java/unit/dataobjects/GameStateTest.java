package unit.dataobjects;

import org.junit.jupiter.api.Test;

import messaging.dataobjects.GameState;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class GameStateTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(GameState.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(GameState.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(GameState.class, hasValidBeanEquals());
    }
}
