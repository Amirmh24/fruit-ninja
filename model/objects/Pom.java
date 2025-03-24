package model.objects;

public class Pom extends Fruit{
    public Pom() {
        this.name="pom";
        this.score=2;
        this.objectType=ObjectType.Fruit;
        load();
    }
}
