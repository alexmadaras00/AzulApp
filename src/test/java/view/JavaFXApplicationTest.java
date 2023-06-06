package view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.condition.DisabledIf;
import org.testfx.api.FxToolkit;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class JavaFXApplicationTest {

    @BeforeAll
    public static void setupSpec() throws Exception {
        System.out.println(java.awt.GraphicsEnvironment.isHeadless());
        if (!java.awt.GraphicsEnvironment.isHeadless()) {
            FxToolkit.registerPrimaryStage();
        }
    }
}
