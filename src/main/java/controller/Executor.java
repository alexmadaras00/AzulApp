package controller;

import dataobjects.DataObject;
import model.Model;

public interface Executor {

    DataObject execute(Model model);

}
