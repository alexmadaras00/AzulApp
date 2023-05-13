import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.DataObject;
import controller.IController;
import view.Messager;
import view.UI;
import view.View;

public class ViewTest {
    private static class MockLoopDone extends Throwable {
        private static final long serialVersionUID = 1L;
    }

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
        public List<DataObject> updated = new ArrayList<>();
        public int moveRequested = 0;

        @Override
        public void update(DataObject object) {
        }

        @Override
        public DataObject getMove() {
            return null;
        }
    }

    private static class MockController implements IController {
        public List<DataObject> notified = new ArrayList<>();

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
