package snake;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //rotate no umändere damits ned so kopiert usgset
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private Direction d;
    private SnakeHead snakeHead;
    private ArrayList<SnakeBody> snakeBody;
    private Item item;
    private Double[] velocity;
    private String direction;
    //Images
    private Image mice;
    private Image background;
    private Image backgroundGras;
    private Image snakeHeadImg;
    private Image snakeBodyImg;
    private Image snakeTailImg;
    private Image snakeBodyCurvedImg;
    private Image snakeBodyFullImg;
    //AnimationTimer
    private AnimationTimer t = new AnimationTimer() {

        private long lastUpdated = 0;

        @Override
        public void handle(long now) {
            if (now - lastUpdated >= 200_000_000) {
                snakeBody.remove(0);
                snakeBody.add(new SnakeBody(new Point2D(snakeHead.getHead().getX(), snakeHead.getHead().getY()), direction));
                System.out.println("snakeTailImg" + snakeBody);
                checkIfSnakeIsOnFood();
                //move snakeHead
                if (dead(snakeHead.getHead(), velocity[0], velocity[1])) {
                    System.out.println("you died");
                    t.stop();
                } else {
                    snakeHead.setHead(new Point2D(snakeHead.getHead().getX() + velocity[0], snakeHead.getHead().getY() + velocity[1]));
                }
                if (gameWon()) {
                    System.out.println("you won");
                    t.stop();
                }
                draw();
                lastUpdated = now;
            }
        }
    };


    public boolean dead(Point2D p, double x, double y) {
        boolean dead = false;
        if (p.getX() + x > 480 ||
                p.getX() + x < 15 ||
                p.getY() + y > 480 ||
                p.getY() + y < 15) {
            dead = true;
        }
        for (int i = 0; i < snakeBody.size() - 2; i++) {
            if (snakeBody.get(i).getX() == snakeHead.getHead().getX() &&
                    snakeHead.getHead().getY() == snakeBody.get(i).getY()) {
                dead = true;
            }
        }

        return dead;
    }

    public boolean gameWon() {
        if (snakeBody.size() == 16 * 16) {
            return true;
        } else {
            return false;
        }

    }

    public void draw() {
        int x = 30;
        gc.clearRect(0, 0, 510, 510);
        //drawBackground();
        gc.drawImage(background, 0, 0, 510, 510);
        //draw Food
        //gc.setFill(Color.GREEN);
        //gc.fillRect(item.getFood().getX(), item.getFood().getY(), 20, 20);

        //draw SnakeHead
        gc.setFill(Color.BLACK);
        //gc.fillRect(snakeHead.getHead().getX(), snakeHead.getHead().getY(), x, x);
        /*gc.save();
        gc.setTransform(1.0, 0.0, 0.0, 1.0, snakeHeadImg.getWidth() / 2.0, snakeHeadImg.getHeight() / 2.0);
        gc.rotate(45);
        gc.drawImage(snakeHeadImg, snakeHead.getHead().getX(), snakeHead.getHead().getY(), x, x);
        gc.restore();*/
        System.out.println("snakeHead" + snakeHead.getHead());
        for (int i = 0; i < snakeBody.size(); i++) {
            SnakeBody s = snakeBody.get(i);
            if (s.equals(snakeBody.get(0))) {
                //draw tail
                drawRotatedImage(gc, snakeTailImg, d.getDirection(s.getDirection()), s.getX(), s.getY());
            } else if (s.getEating()) {
                gc.setFill(Color.RED);
                gc.drawImage(snakeBodyFullImg, s.getX(), s.getY(), 30, 30);
            } else if (!s.getDirection().equals(snakeBody.get(i - 1).getDirection())) {
                //draw curvedBody
                String firstDirection = snakeBody.get(i - 1).getDirection();
                String secondDirection = s.getDirection();
                if (firstDirection.equals("right") && secondDirection.equals("up") ||
                        firstDirection.equals("up") && secondDirection.equals("left") ||
                        firstDirection.equals("left") && secondDirection.equals("down") ||
                        firstDirection.equals("down") && secondDirection.equals("right")) {
                    drawRotatedImage(gc, snakeBodyCurvedImg, d.getDirection(s.getDirection()), s.getX(), s.getY());
                } else {
                    drawRotatedImage(gc, snakeBodyCurvedImg, d.getDirection(s.getDirection()) + 90, s.getX(), s.getY());
                }

            } else {
                //draw
                //gc.fillRect(p.getX(), p.getY(), x, x);
                //gc.drawImage(snakeBodyImg, s.getX(), s.getY(), x, x);
                drawRotatedImage(gc, snakeBodyImg, d.getDirection(s.getDirection()), s.getX(), s.getY());
            }
        }
        gc.drawImage(mice, item.getFood().getX(), item.getFood().getY(), x, x);
        drawRotatedImage(gc, snakeHeadImg, d.getDirection(direction), snakeHead.getHead().getX(), snakeHead.getHead().getY());

        gc.drawImage(backgroundGras, 0, 0, 510, 510);
        System.out.println("---");
    }


    public void checkIfSnakeIsOnFood() {
        if (snakeHead.getHead().getX() == item.getFood().getX() && snakeHead.getHead().getY() == item.getFood().getY()) {
            System.out.println("food");
            //add snakeHead tail on placce where snakeHead is

            snakeBody.add(new SnakeBody(new Point2D(snakeHead.getHead().getX(), snakeHead.getHead().getY()), direction, true));

            item.creatFood(snakeBody);
        }
    }

    //zum luege obs mit dem gat

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    // zum luege obs mit dem gat

    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + 15, tlpy + 15);
        gc.drawImage(image, tlpx, tlpy, 30, 30);
        gc.restore(); // back to original state (before rotation)
    }

    @FXML
    public void onKeyReleased(KeyEvent keyEvent) {
        double x = 30.0;
        switch (keyEvent.getCode().getName()) {
            case "Up":
                System.out.println("up");
                if (direction != "down") {
                    direction = "up";
                    velocity[0] = 0.0;
                    velocity[1] = -x;
                }
                break;
            case "Down":
                System.out.println("down");
                if (direction != "up") {
                    direction = "down";
                    velocity[0] = 0.0;
                    velocity[1] = x;
                    System.out.println("down");
                }
                break;
            case "Right":
                System.out.println("right");
                if (direction != "left") {
                    direction = "right";
                    velocity[0] = x;
                    velocity[1] = 0.0;

                }
                break;
            case "Left":
                System.out.println("left");
                if (direction != "right") {
                    direction = "left";
                    velocity[0] = -x;
                    velocity[1] = 0.0;
                }
                break;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        snakeHead = new SnakeHead();
        snakeBody = new ArrayList<>();
        //for (int i = 0; i < 500; i++) {
        snakeBody.add(new SnakeBody(new Point2D(15, 15), "right"));
        snakeBody.add(new SnakeBody(new Point2D(45, 15), "right"));
        //}
        item = new Item();
        item.creatFood(snakeBody);
        d = new Direction();
        velocity = new Double[2];
        velocity[0] = 30.0;
        velocity[1] = 0.0;
        direction = "right";
        //images
        mice = new Image("images/mice.png");
        background = new Image("images/background.png");
        backgroundGras = new Image("images/background2.png");
        snakeHeadImg = new Image("images/snakeHead.png");
        snakeBodyImg = new Image("images/snakeBody.png");
        snakeTailImg = new Image("images/snakeTail.png");
        snakeBodyCurvedImg = new Image("images/snakeBodyCurved.png");
        snakeBodyFullImg = new Image("snakeBodyFull.png");
        t.start();
        draw();
    }

}
