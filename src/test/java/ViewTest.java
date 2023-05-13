import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private static class MockLoopDone extends RuntimeException {
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
        public static final int MAX_MOVE_REQUESTED = 10;

        @Override
        public void update(DataObject object) {
            updated.add(object);
        }

        @Override
        public DataObject getMove() {
            if (moveRequested < MAX_MOVE_REQUESTED) {
                moveRequested++;
                return MockDataObject.MOVE;
            } else {
                throw new MockLoopDone();
            }
        }
    }

    private static class MockController implements IController {
        public List<DataObject> notified = new ArrayList<>();
        private Messager view;

        public MockController(Messager view) {
            this.view = view;
        }

        @Override
        public void notify(Object sender, DataObject message) {
            notified.add(message);
            view.handleResponse(MockDataObject.UPDATE);
        }
    }

    private static Messager view;
    private static MockUI ui;
    private static MockController controller;

    @BeforeAll
    static void setUp() {
        ui = new MockUI();
        view = new View(ui);
        controller = new MockController(view);
        try {
            view.connectController(controller);
        } catch (MockLoopDone e) {
            // Loop done
        }
    }

    @Test
    void testAllLoops() {
        assertEquals(MockUI.MAX_MOVE_REQUESTED, ui.moveRequested);
    }

    @Test
    void testAllUpdates() {
        assertEquals(MockUI.MAX_MOVE_REQUESTED, ui.updated.size());
        for (DataObject object : ui.updated) {
            assertEquals(MockDataObject.UPDATE, object);
        }
    }

    @Test
    void testAllNotifications() {
        assertEquals(MockUI.MAX_MOVE_REQUESTED, controller.notified.size());
        for (DataObject object : controller.notified) {
            assertEquals(MockDataObject.MOVE, object);
        }
    }
}
