import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import com.google.code.beanmatchers.ValueGenerator;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import dataobjects.Move;

import java.util.OptionalInt;
import java.util.Random;

public class MoveTest {

    @BeforeAll
    public static void setup() {
        registerValueGenerator(new ValueGenerator<OptionalInt>() {
            public OptionalInt generate() {
                if (new Random().nextInt(5) == 1) {
                    return OptionalInt.empty();
                }
                return OptionalInt.of(new Random().nextInt(100)); // Change to generate random instance
            }
        }, OptionalInt.class);
    }

    @Test
    public void testNoArgsConstructor() {
        assertThat(Move.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(Move.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(Move.class, hasValidBeanEquals());
    }
}
