import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import controller.DOExecutorFactory;
import controller.Executor;
import controller.ExecutorFactory;
import dataobjects.DataObject;
import dataobjects.Move;
import model.Model;

public class DOExecutorFactoryTest {
    private static enum MockDataObject implements DataObject {
        MOVE, UPDATE;
    }

    private static class MockModel implements Model {
        public int didSomethingElseCounter = 0;
        public int didSomethingCounter = 0;

        // implement each method from model with counter of execution, returns UPDATE
        public DataObject doSomething() {
            didSomethingCounter++;
            return MockDataObject.UPDATE;
        }

        public DataObject doSomethingElse() {
            didSomethingElseCounter++;
            return MockDataObject.UPDATE;
        }
    }

    private static ExecutorFactory factory;
    private static MockModel model;

    @BeforeEach
    public static void setUp() {
        model = new MockModel();
        factory = new DOExecutorFactory();
    }

    @Disabled
    public void testMoveMessage() {
        // TODO EXAMPLE test per datatype
        Executor executor = factory.createExecutor(new Move());
        DataObject dataobject = executor.execute(model);
        assertEquals(MockDataObject.UPDATE, dataobject);
        assertEquals(1, model.didSomethingCounter);
        assertEquals(0, model.didSomethingElseCounter);
    }
}
