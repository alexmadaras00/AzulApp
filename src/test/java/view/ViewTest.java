package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.Mediator;
import dataobjects.DataObject;
import model.Model;
import view.Messager;
import view.UI;
import view.View;

public class ViewTest {
    private static enum MockDataObject implements DataObject {
        MOVE,
        UPDATE;
    }

    private static class MockUI implements UI {
        public List<DataObject> notifications = new ArrayList<>();

        @Override
        public void update(DataObject object) {
            notifications.add(object);
        }
    }

    private static class MockController implements Mediator {
        public List<DataObject> notifications = new ArrayList<>();
        private Messager messager;

        @Override
        public void notify(DataObject message) {
            notifications.add(message);
            messager.notify(MockDataObject.UPDATE);
        }

        @Override
        public void connectMessager(Messager messager) {
            this.messager = messager;
        }

        @Override
        public void connectModel(Model model) {
            return;
        }

    }

    private static Messager view;
    private static MockUI ui;
    private static MockController controller;

    @BeforeAll
    static void setUp() {
        ui = new MockUI();
        view = new View(ui);
        controller = new MockController();
        controller.connectMessager(view);
        view.connectMediator(controller);
    }

    @Test
    void testConnectionViewToController() {
        view.send(MockDataObject.MOVE);
        assertEquals(1, controller.notifications.size());
        assertEquals(MockDataObject.MOVE, controller.notifications.get(0));
        assertEquals(1, ui.notifications.size());
        assertEquals(MockDataObject.UPDATE, ui.notifications.get(0));

    }
}
