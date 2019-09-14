package snake;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

public class Item {
    private Point2D food;
    private Random r;

    public Item() {
        r = new Random();
    }

    public void creatFood(ArrayList<SnakeBody> snakeBody) {
        //check if not snake is there
        double x = r.nextInt(16) * 30 + 15;
        double y = r.nextInt(16) * 30 + 15;
        boolean foodCantBePlaced = true;
        while (foodCantBePlaced){
            foodCantBePlaced = false;
            for (SnakeBody s : snakeBody) {
                if (s.getX() == x && s.getY() == y) {
                    foodCantBePlaced = true;
                    x = r.nextInt(16) * 30 + 15;
                    y = r.nextInt(16) * 30 + 15;
                }
            }
    }
        setFood(new Point2D(x, y));
    }


    public Point2D getFood() {
        return food;
    }

    public void setFood(Point2D food) {
        this.food = food;
    }
}
