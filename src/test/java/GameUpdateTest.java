import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import com.google.code.beanmatchers.ValueGenerator;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import dataobjects.GameUpdate;

import model.Color;
import model.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GameUpdateTest {

    @BeforeAll
    public static void setup() {
        registerValueGenerator(new ValueGenerator<Optional>() {
            public Optional generate() {
                if (new Random().nextInt(5) == 1) {
                    return Optional.ofNullable(null);
                }
                if (new Random().nextInt(2) == 1) {
                    List<List<Tile>> table = new ArrayList<>();
                    for (int i = 0; i< new Random().nextInt(7); i++) {
                        table.add(new ArrayList<>());
                        for (int j = 0; j< new Random().nextInt(7); j++) {
                            table.get(i).add(Color.values()[new Random().nextInt(Color.values().length)]);
                        }
                    }
                    return Optional.ofNullable(table); 
                } else {
                    List<Tile> tiles = new ArrayList<>();
                    for (int i = 0; i< new Random().nextInt(7); i++) {
                        tiles.add(Color.values()[new Random().nextInt(Color.values().length)]);
                    }
                    return Optional.ofNullable(tiles);
                }
            }
        }, Optional.class);
    }

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
