package model.objects;

public class Apple extends Fruit{
    public Apple() {
        this.name="apple";
        this.score=2;
        this.objectType=ObjectType.Fruit;
        load();
    }
}
