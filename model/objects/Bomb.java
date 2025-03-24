package model.objects;

import model.entities.Player;

public class Bomb extends Sliceable {
    int risk;


    public ObjectType getObjectType() {
        return objectType;
    }

    public void slice() {
        if (!isSliced()) {
            super.slice();
            Player.get_playerInstance().dispenseLive(risk);
        }
    }
}
