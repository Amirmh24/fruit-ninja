package model.objects;

public class Orange extends Fruit{
    public Orange() {
        this.name="orange";
        this.score=2;
        this.objectType=ObjectType.Fruit;
        load();
    }
}
