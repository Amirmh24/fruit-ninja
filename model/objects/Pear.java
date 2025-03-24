package model.objects;

public class Pear extends Fruit{
    public Pear() {
        this.name="pear";
        this.score=2;
        this.objectType=ObjectType.Fruit;
        load();
    }
}