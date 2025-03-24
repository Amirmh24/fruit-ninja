package model.objects;

public class Lemon extends Fruit{
    public Lemon() {
        this.name="lemon";
        this.score=2;
        this.objectType=ObjectType.Fruit;
        load();
    }
}