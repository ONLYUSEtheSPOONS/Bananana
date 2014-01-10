/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bananana;

import environment.Environment;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author doughill
 */
class BanananaEnvironment extends Environment {

    private Grid grid;
    private int score = 0;
    private Snake snake;
    private int speed = 5;
    private int moveCounter = speed;

    public BanananaEnvironment() {
    }

    @Override
    public void initializeEnvironment() {


        this.setBackground(ResourceTools.loadImageFromResource("resources/grass.jpg"));
        this.grid = new Grid();
        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(4, 3));

        this.grid.setColor(Color.BLACK);
        this.grid.setColumns(42);
        this.grid.setRows(26);
        this.grid.setCellWidth(20);
        this.grid.setShowCellCoordinates(false);
        this.grid.setPosition(new Point(50, 50));


    }

    @Override
    public void timerTaskHandler() {
        System.out.println("timer");
        if (snake != null) {
            if (moveCounter <= 0) {
                snake.move();
                moveCounter = speed;
            } else {
                moveCounter--;
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.score += 5;
        } else if (e.getKeyCode() == KeyEvent.VK_Z) {
            snake.move();

        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            snake.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            snake.setDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (this.grid != null) {
            this.grid.paintComponent(graphics);

            Point cellLocation;
            graphics.setColor(Color.BLUE);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());

                }
            }
        }


        graphics.setFont(new Font("Calibri", Font.ITALIC, 50));
        graphics.setColor(Color.MAGENTA);
        graphics.drawString("SCORE:" + this.score, 50, 50);


    }
}
