package model.objects;

public interface GameObject {

    ObjectType getObjectType();

    public Boolean isSliced();

    public Boolean hasMovedOffScreen();

    public void slice();

    public void move();
}
