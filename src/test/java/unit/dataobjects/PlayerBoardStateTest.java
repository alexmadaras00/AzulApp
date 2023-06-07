package unit.dataobjects;

import org.junit.jupiter.api.Test;

import dataobjects.data.PlayerBoardState;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class PlayerBoardStateTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(PlayerBoardState.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(PlayerBoardState.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(PlayerBoardState.class, hasValidBeanEquals());
    }
}
