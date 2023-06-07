package integration.dataobjects;

import dataobjects.DataObjectExecutorFactory;
import dataobjects.Executable;
import dataobjects.ExecutorFactory;
import dataobjects.RoundUpdate;
import dataobjects.executors.RoundUpdateExecutor;
import model.Game;
import model.Model;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GUI;
import view.Messager;
import view.UI;
import view.View;

public class RoundUpdateExecutorTest {

    static RoundUpdateExecutor roundUpdateExecutor;
    static ExecutorFactory executorFactory;
    static View ui;
    @BeforeEach
    public void setUp() {
        roundUpdateExecutor = new RoundUpdateExecutor();
        executorFactory = new DataObjectExecutorFactory();
        ui = new View(executorFactory);
    }
    @Test
    public void testExecute(){
        Executable executable;

    }
}
