package model.factory;

public class TwinteamFactoryCreator implements FactoryCreator {

    @Override
    public FactoryInterface createFactory() {
        return new FactoryTwinteamWrapper();
    }

}
