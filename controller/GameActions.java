package controller;

import model.objects.Sliceable;

public interface GameActions {
//    created game object
    Sliceable createGameObject();

//     saves the current state of the game
    void saveGame();

//     loads the last saved state of the game
    void loadGame();

//     resets the game to its initial state
    void resetGame();
}
