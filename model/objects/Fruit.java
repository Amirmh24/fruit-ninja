package model.objects;

import model.entities.Player;

public class Fruit extends Sliceable{
    protected int score;

    public int getScore() {
        return score;
    }

    public ObjectType getObjectType(){
        return objectType;
    }

    public void slice() {
        if (!isSliced()) {
            super.slice();
            Player.get_playerInstance().addScore(score);
        }
    }
}
