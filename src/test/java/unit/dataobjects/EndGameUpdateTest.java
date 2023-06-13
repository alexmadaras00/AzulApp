package unit.dataobjects;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import messaging.dataobjects.EndGameUpdate;

public class EndGameUpdateTest{

     @Test
    public void testNoArgsConstructor() {
        assertThat(EndGameUpdate.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(EndGameUpdate.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(EndGameUpdate.class, hasValidBeanEquals());
    }
}