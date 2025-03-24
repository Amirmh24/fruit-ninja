package model.entities;

import javafx.scene.shape.QuadCurveTo;

import java.util.Random;

public class Projectile {
    private final double _xInitial;
    private final double _yInitial;
    private final double _yFinal;
    private final double _maxHeight;
    private final double _thetaInitial;
    private double _controlX;
    private double _controlY;
    private double _xFinal;
    private double _velocityInitial;
    private double _time;
    private double _deltaX;

    //Constructing projectile using projectile dynamics laws
    public Projectile() {
        Random rnd = new Random();
        _thetaInitial = Math.toRadians(getRandomWithExclusion(rnd, 88, 92, 90)); //theta randomly generated
        _maxHeight = (Math.random() * 100) + 450; // randomly generated
        _xInitial = (Math.random() * (800) + 100); // randomly generated
        _yInitial = 650; //constant
        _yFinal = _yInitial;
    }

    public double getXInitial() {
        return _xInitial;
    }

    public double getYInitial() {
        return _yInitial;
    }

    public double get_time() {
        return _time;
    }

    public QuadCurveTo constructPath() {
        doMaths();
        return new QuadCurveTo(_controlX, _controlY, _xFinal, _yFinal);
    }

    public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    public void doMaths() {
        double slope;
        _velocityInitial = Math.sqrt(_maxHeight * 2 * 9.81 / Math.pow(Math.sin(_thetaInitial), 2));
        _xFinal = _xInitial + Math.pow(_velocityInitial, 2) * Math.sin(2 * _thetaInitial) / 9.81;
        _deltaX = _xFinal - _xInitial;
        _time = 2 * Math.abs(_velocityInitial * Math.sin(_thetaInitial) / 9.81);
        _controlX = (_xFinal + _xInitial) / 2;
        slope = -1 * (Math.tan(_thetaInitial));
        _controlY = slope * (_controlX - _xInitial) + 600;
    }

    public double get_deltaX() {
        return _deltaX;
    }

    public void set_deltaX(double _deltaX) {
        this._deltaX = _deltaX;
    }

}