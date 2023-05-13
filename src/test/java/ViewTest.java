import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.DataObject;
import controller.IController;
import view.Messager;
import view.UI;
import view.View;

public class ViewTest {
    private static enum MockDataObject implements DataObject {
        MOVE,
        UPDATE;

        @Override
        public JSONObject toJSON() {
            return null;
        }

        @Override
        public DataObject fromJSON(JSONObject object) {
            return null;
        }
    }

    private static class MockUI implements UI {
        public DataObject updated;
        public boolean moveRequested;

        @Override
        public void update(DataObject object) {
        }

        @Override
        public DataObject getMove() {
            return null;
        }
    }

    private static class MockController implements IController {
        public DataObject notified;

        @Override
        public void notify(Object sender, DataObject message) {

        }
    }

    private static Messager view;
    private static MockUI ui;
    private static IController controller;

    @BeforeAll
    static void setUp() {

    }

    @Test
    void testConnectController() {

    }
}
