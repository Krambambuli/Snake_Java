package snake;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class SnakeHead {
    private Point2D head;


    public SnakeHead(){
        head = new Point2D(75, 15);

    }


    public Point2D getHead() {
        return head;
    }

    public void setHead(Point2D snakeHead) {
        this.head = snakeHead;
    }

}
