package model.entities;

import model.Observer;
import model.Subject;
import controller.GameController;

import java.util.ArrayList;

public class Player implements Subject {
    private static Player _playerInstance = null;
    private final ArrayList<Observer> _observers = new ArrayList<>();
    private int _score;
    private int _lives;
    private boolean _alive;

    private Player() {
        _alive = true;
        _lives = 3;
        _score = 0;
    }

    public static Player get_playerInstance() {
        if (_playerInstance == null)
            _playerInstance = new Player();

        return _playerInstance;
    }

    public void resetPlayer() {
        _playerInstance = null;
    }

    public int get_lives() {
        return _lives;
    }

    public int get_score() {
        return Player.get_playerInstance()._score;
    }

    public void set_score(int _score) {
        Player.get_playerInstance()._score = _score;
        notifyObserver();
    }

    public void addScore(int score) {
        Player.get_playerInstance()._score += score;
        notifyObserver();
    }

    public void dispenseLive(int risk) {
        Player.get_playerInstance()._lives -= risk;
        if (_lives <= 0) {
            _lives = 0;
            die();
        }
        notifyObserver();
    }

    public void die() {
        _lives = 0;
        set_alive(false);
        notifyObserver();
        GameController.get_instance().endGame();
    }

    public boolean is_alive() {
        return _alive;
    }

    public void set_alive(boolean _alive) {
        this._alive = _alive;
    }

    @Override
    public void register(Observer newObserver) {
        this._observers.add(newObserver);
    }

    @Override
    public void unregister(Observer deletedObserver) {
        this._observers.remove(deletedObserver);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : _observers) {
            System.out.println(observer);
            observer.update(_lives, _score);
        }
    }

}
