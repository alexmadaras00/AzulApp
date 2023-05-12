import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import com.google.code.beanmatchers.ValueGenerator;

import static com.google.code.beanmatchers.BeanMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;

import dataobjects.PlayerData;
import dataobjects.PlayerBoardState;

import java.util.Optional;
import java.util.Random;



public class PlayerDataTest {

    @BeforeAll
    public static void setup() {
        registerValueGenerator(new ValueGenerator<Optional>() {
            public Optional generate() {
                if (new Random().nextInt(5) == 1) {
                    return Optional.ofNullable(null);
                }
                if (new Random().nextInt(2) == 1) {
                    String[] randomNames = new String[] {"player 1", "steve", "Kirk", "Clark", "RandomPlayer"};
                    return Optional.ofNullable(randomNames[new Random().nextInt(5)]); 
                } else {
                    // To properly test comparison, it should create a random PlayerBoardState(). But given that we tested comparison of 
                    // PlayerBoardState in PlayerBoardStateTest already, it is not worth the effort of randomly creating one here. 
                    return Optional.ofNullable(new PlayerBoardState());
                }
                
            }
        }, Optional.class);
    }

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
