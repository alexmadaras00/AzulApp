package unit.dataobjects;

import org.junit.jupiter.api.Test;

import messaging.dataobjects.Location;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocationTest {

    @Test
    public void testNoArgsConstructor() {
        assertThat(Location.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(Location.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(Location.class, hasValidBeanEquals());
    }
}
