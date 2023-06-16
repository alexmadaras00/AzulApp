package model.factory;

public class OurFactoryCreator implements FactoryCreator {

    @Override
    public FactoryInterface createFactory() {
        return new Factory();
    }

}
