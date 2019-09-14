package snake;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class SnakeBody {
    private Point2D tail;
    private String direction;
    private boolean eating;

    public SnakeBody(Point2D p, String d){
        tail = p;
        direction = d;
        eating = false;
    }

    public SnakeBody(Point2D p, String d, boolean t){
        tail = p;
        direction = d;
        eating = t;
    }


    public Point2D getTail() {
        return tail;
    }

    public void setTail(Point2D tail) {
        this.tail = tail;
    }

    public Double getX(){
        return tail.getX();
    }

    public Double getY(){
        return tail.getY();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean getEating(){
        return eating;
    }
}
