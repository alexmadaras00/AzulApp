package unit.dataobjects;

import org.junit.jupiter.api.Test;

import messaging.dataobjects.PlayerData;

import static com.google.code.beanmatchers.BeanMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;

public class PlayerDataTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(PlayerData.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(PlayerData.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(PlayerData.class, hasValidBeanEquals());
    }
}
