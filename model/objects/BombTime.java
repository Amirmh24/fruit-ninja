package model.objects;

public class BombTime extends Bomb{
    public BombTime() {
        this.name="bombTime";
        this.risk=3;
        this.objectType=ObjectType.Bomb;
        load();
    }
}
