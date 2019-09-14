package snake;

import java.util.HashMap;

public class Direction {

    private HashMap<String,Integer> direction;

    public Direction(){
        direction = new HashMap<>();
        direction.put("up", 180);
        direction.put("down", 0);
        direction.put("left", 90);
        direction.put("right", -90);
    }


    public Integer getDirection(String d) {
        return direction.get(d);
    }
}
