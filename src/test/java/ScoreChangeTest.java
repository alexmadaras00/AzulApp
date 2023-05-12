import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.ValueGenerator;

import static com.google.code.beanmatchers.BeanMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;

import model.Color;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

import dataobjects.ScoreChange;

public class ScoreChangeTest {

    @BeforeAll
    public static void setup() {
        registerValueGenerator(new ValueGenerator<Optional>() {
            public Optional generate() {
                if (new Random().nextInt(5) == 1) {
                    return Optional.ofNullable(null);
                }
                return Optional.ofNullable(Color.values()[new Random().nextInt(Color.values().length)]); 
            }
        }, Optional.class);

        registerValueGenerator(new ValueGenerator<OptionalInt>() {
            public OptionalInt generate() {
                if (new Random().nextInt(5) == 1) {
                    return OptionalInt.empty();
                }
                return OptionalInt.of(new Random().nextInt(100));
            }
        }, OptionalInt.class);
    }

    @Test
    public void testNoArgsConstructor() {
        assertThat(ScoreChange.class, hasValidBeanConstructor());
    }

    @Test
    public void testSettersAndGetters() {
        assertThat(ScoreChange.class, hasValidGettersAndSetters());
    }
    
    @Test
    public void allPropertiesShouldBeComparedDuringEquals() {
        assertThat(ScoreChange.class, hasValidBeanEquals());
    }
}
