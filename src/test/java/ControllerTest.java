import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.Executor;
import controller.ExecutorFactory;
import controller.Mediator;
import dataobjects.DataObject;
import model.Model;
import view.Messager;

public class ControllerTest {

    private enum MockDataObject implements DataObject {
        MOVE, UPDATE;
    }

    private static class MockMessager implements Messager {
        public List<DataObject> notifications = new LinkedList<DataObject>();
        private Mediator mediator;

        @Override
        public void connectMediator(Mediator mediator) {
            this.mediator = mediator;
        }

        @Override
        public void notify(DataObject message) {
            notifications.add(message);
        }

        @Override
        public void send(DataObject message) {
            mediator.notify(message);
        }
    }

    private static class MockModel implements Model {
        public int didSomethingCounter;

        public MockModel() {
            didSomethingCounter = 0;
        }

        public DataObject doSomething() {
            didSomethingCounter++;
            return MockDataObject.UPDATE;
        }
    }

    private static class MockExecutor implements Executor {

        public MockExecutor(DataObject message) {}

        @Override
        public DataObject execute(Model model) {
            MockModel newModel = (ControllerTest.MockModel) model;
            return newModel.doSomething();
        }

    }

    private static class MockExecutorFactory implements ExecutorFactory {

        @Override
        public Executor createExecutor(DataObject message) {
            return new MockExecutor(message);
        }
    }

    private static Mediator controller;
    private static MockModel model;
    private static MockMessager messager;

    @BeforeAll
    public static void setUp() {
        model = new MockModel();
        messager = new MockMessager();
        controller = new Controller(new MockExecutorFactory());
        messager.connectMediator(controller);
        controller.connectMessager(messager);
        controller.connectModel(model);
    }

    @Test
    public void testConnection() {
        assertEquals(0, model.didSomethingCounter);
        assertEquals(0, messager.notifications.size());
        messager.send(MockDataObject.MOVE);
        assertEquals(1, model.didSomethingCounter);
        assertEquals(1, messager.notifications.size());
        assertEquals(MockDataObject.UPDATE, messager.notifications.get(0));
    }
}
