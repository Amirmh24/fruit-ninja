package model.objects;

public class BombFatal extends Bomb{

    public BombFatal() {
        this.name="bombFatal";
        this.risk=1;
        this.objectType=ObjectType.Bomb;
        load();
    }
}
