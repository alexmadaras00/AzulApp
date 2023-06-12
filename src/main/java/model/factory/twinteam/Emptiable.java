package model.factory.twinteam;


public interface Emptiable {

    public void empty();

    public void emptyInto(TileCollection collection) throws CollectionOverCapacityException;
}
