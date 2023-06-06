package model.factory;

public class ConcreteFactoryCreator implements FactoryCreator {

    @Override
    public FactoryInterface createFactory() {
        return new Factory();
    }
    
}
