package model.factory;

public class ConcreteTwinteamFactoryCreator implements FactoryCreator{

    @Override
    public FactoryInterface createFactory() {
        return new FactoryTwinteamWrapper();
    }
    
}
