package model.objects;

import controller.Game;
import model.entities.Player;
import model.entities.Projectile;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import controller.GameController;

public abstract class Sliceable implements GameObject{
    protected Media media;
    protected String name;
    protected Image[] images = new Image[3];
    protected ImageView node;
    protected Projectile projectile;
    protected int xLocation;
    protected int yLocation;
    protected boolean movedOffScreen = false;
    protected boolean sliced = false;
    protected ParallelTransition pt;
    protected FadeTransition ft;
    protected boolean faded = false;
    protected double animationTime;
    protected ObjectType objectType;
    public ImageView getNode() {
        return node;
    }

    protected void setNode(ImageView node) {
        this.node = node;
    }

    @Override
    public ObjectType getObjectType() {
        return null;
    }

    @Override
    public Boolean isSliced() {
        return this.sliced;
    }

    @Override
    public Boolean hasMovedOffScreen() {
        return this.movedOffScreen;
    }

    @Override
    public void slice() {
        node.setImage(images[1]);
        setSliced(true);
        fade();
    }

    @Override
    public void move() {
        Path path = new Path();
        path.getElements().add(new MoveTo(projectile.getXInitial(), projectile.getYInitial()));
        path.getElements().add(projectile.constructPath());
        animationTime = 0.2 * projectile.get_time();
        PathTransition pathTransition = new PathTransition(Duration.seconds(animationTime), path, this.getNode());
        RotateTransition rt = new RotateTransition(Duration.seconds(4), this.node);
        rt.setAutoReverse(false);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.setByAngle(180);
        rt.setCycleCount(10);
        pathTransition.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                if (t < 0.5) {
                    return -2 * Math.pow(t - 0.5, 2) + 0.5;
                } else {
                    return 2 * Math.pow(t - 0.5, 2) + 0.5;
                }
            }
        });
        pathTransition.setAutoReverse(false);
        pathTransition.setCycleCount(1);
        pathTransition.setOnFinished(e -> {
            movedOffScreen = true;
            if (Player.get_playerInstance().is_alive()) {
                GameController.get_instance().checkMovedOffScreen(this);
                Game.getGameObjects().remove(this);
            }
        });
        pt = new ParallelTransition();
        pt.getChildren().addAll(pathTransition, rt);
        pt.playFromStart();
    }

    protected void load() {
        this.projectile = new Projectile();
        String path = "/view/resources/";
        this.images[0] = new Image(path + this.name + ".png");
        if (!(this.name.equals("bombFatal") || this.name.equals("bombTime"))) {
            this.images[1] = new Image(path + this.name + "Split.png");
        }
        this.node = new ImageView(images[0]);
    }

    public void fade() {
        if (!faded) {
            faded = true;
            this.ft = new FadeTransition(Duration.seconds(1), this.node);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
        }
    }

    public ParallelTransition getTransition() {
        return pt;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected Image[] getImages() {
        return images;
    }

    protected void setImages(Image[] images) {
        this.images = images;
    }

    protected void setSliced(boolean sliced) {
        this.sliced = sliced;
    }

    protected Projectile getProjectile() {
        return projectile;
    }

    protected void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public boolean isFaded() {
        return faded;
    }

    public void setFaded(boolean faded) {
        this.faded = faded;
    }
}
