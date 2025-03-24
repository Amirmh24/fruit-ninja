package controller;

import model.Observer;
import model.entities.Player;
import model.objects.ObjectType;
import model.objects.Sliceable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Observer, Initializable {

    @FXML
    public AnchorPane gameCanvas;
    @FXML
    public ImageView lives;
    @FXML
    public Label currentScore;
    @FXML
    public Label maxScore;
    @FXML
    public Label timerLabel;
    private float _objCreationRate;
    private Timeline _throwTimeline, _timerTimeline;
    private boolean _isPaused = false;
    private int _globalTimer;
    private static KeyFrame _addObjKeyFrame;
    private static KeyFrame _timerKeyFrame;
    private static GameController _instance;
    private final Image[] _livesImages = new Image[4];

    public static GameController get_instance() {
        if (_instance == null) {
            _instance = new GameController();
        }
        return _instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();
        maxScore.setText("Best: " + Game.getGameInstance().getMaximumScore());
        _instance = this;
        _globalTimer = 0;
        _objCreationRate = 0.5f;
        throwPeriodic();
        _throwTimeline.play();
        timer();
        _timerTimeline.play();
        Player.get_playerInstance().register(this);
    }

    private void load() {
        Game.getGameInstance().loadGame();
        String path = "/view/resources/";
        for (int i = 0; i <= 3; i++) {
            _livesImages[i] = new Image(path + "lives" + i + ".png");
        }
    }

    private void throwPeriodic() {
        _addObjKeyFrame = new KeyFrame(Duration.seconds(_objCreationRate), e -> {
            throwGameObject();
        });
        _throwTimeline = new Timeline(_addObjKeyFrame);
        _throwTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void timer() {
        _timerKeyFrame = new KeyFrame(Duration.seconds(1), e -> {
            _globalTimer++;
            if (_globalTimer / 60 < 1) {
                if (_globalTimer % 60 < 10) {
                    timerLabel.setText("0" + (_globalTimer / 60) + ":0" + _globalTimer % 60);
                } else {
                    timerLabel.setText("0" + (_globalTimer / 60) + ":" + _globalTimer % 60);
                }
            } else {
                if (_globalTimer % 60 < 10) {
                    timerLabel.setText((_globalTimer / 60) + ":0" + _globalTimer % 60);
                } else {
                    timerLabel.setText((_globalTimer / 60) + ":" + _globalTimer % 60);
                }
            }
        });
        _timerTimeline = new Timeline(_timerKeyFrame);
        _timerTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void throwGameObject() {
        Sliceable gameObject = Game.getGameInstance().createGameObject();
        Game.getGameObjects().add(gameObject);
        gameCanvas.getChildren().add(gameObject.getNode());
        gameObject.getNode().setTranslateY(700);
        gameCanvas.setOnDragDetected(e -> gameCanvas.startFullDrag());
        gameObject.getNode().setOnMouseDragEntered(e -> {
            gameObject.slice();
        });
        gameObject.move();
    }


    public void endGame() {
        Platform.runLater(() -> {
            Game.getGameObjects().removeAll();
            gameCanvas.getChildren().removeAll();
            _throwTimeline.stop();
            _timerTimeline.stop();
            Player.get_playerInstance().unregister(this);
            Game.getGameInstance().saveGame();
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/view/windows/game-over.fxml"));
                Stage stage = (Stage) gameCanvas.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void checkMovedOffScreen(Sliceable gameObject) {
        if (gameObject.hasMovedOffScreen()) {
            if (!gameObject.isSliced() && gameObject.getObjectType().equals(ObjectType.Fruit)
                    && gameCanvas.getChildren().contains(gameObject.getNode())) {
                Player.get_playerInstance().dispenseLive(1);
            }
            gameCanvas.getChildren().remove(gameObject.getNode());
        }
    }


    @Override
    public void update(int lives, int score) {
        this.lives.setImage(_livesImages[lives]);
        currentScore.setText("Score: "+score);
        if (Game.getGameInstance().getMaximumScore()<score){
            Game.getGameInstance().setMaximumScore(score);
            maxScore.setText("Best: " + score);
        }
    }
}
