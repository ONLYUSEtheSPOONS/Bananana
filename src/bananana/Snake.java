/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bananana;

import static bananana.Direction.UP;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author doughill
 */
public class Snake {
private int growthCount;
    {
        setBody(new ArrayList<Point>());
    }
public boolean selfHitTest(){
    for (int i = 1; i < getBody().size(); i++) {
        if (getBody().get(i).equals(getHead() )) {
            return true;
            
        }
    }
    
    return false;
}

        
    public void move() {
        //create a new location for the head using the direction
        System.out.println("MOOOOVE");
        int x = 0;
        int y = 0;

        switch (getDirection()) {
            case UP:
                x = 0;
                y = -1;
                break;
            case DOWN:
                x = 0;
                y = 1;
                break;
            case RIGHT:
                x = 1;
                y = 0;
                break;
            case LEFT:
                x = -1;
                y = 0;

        }
        getBody().add(0, new Point(getHead().x + x, getHead().y + y));
        if (growthCounter > 0) {
            growthCounter--;
        } else {
            getBody().remove(getBody().size() - 1);
        }
    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    private ArrayList<Point> body;
    private Direction direction = Direction.RIGHT;
    private int growthCounter;

    public Point getHead() {
        return getBody().get(0);
    }

    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    void setGrowthCount(int i) {
        this.growthCounter = growthCounter;
    }

    /**
     * @return the growthCount
     */
    public int getGrowthCount() {
        return growthCounter;
    }
    
    public void grow(int growth) {
        this.growthCounter += growth;
    }
        //</editor-fold>
}
