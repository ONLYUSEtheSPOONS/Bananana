/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bananana;

import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author doughill
 */
class BanananaEnvironment extends Environment {

    private GameState gameState = GameState.PAUSED;
    private Grid grid;
    private int score = 0;
    private Snake snake;
    private int defaultDelay = 4;
    private int delay = defaultDelay;
    private ArrayList<Point> apples;
    private ArrayList<Point> poisonBottles;
    private int delayDefault;
    private Image snitch;
    private ArrayList<Point> goldenSnitches;
    private Image snitch1;
    private ArrayList<Point> blackSnitches;
    private Image apple1;
    private ArrayList<Point> poisonApples;
    private ArrayList<Point> wall;

    public BanananaEnvironment() {
    }

    @Override
    public void initializeEnvironment() {

        this.setBackground(ResourceTools.loadImageFromResource("resources/grass.jpg"));
        this.grid = new Grid();

        this.snitch = ResourceTools.loadImageFromResource("resources/golden_snitch.png");
        this.snitch1 = ResourceTools.loadImageFromResource("resources/black_snitch.png");
        this.apple1 = ResourceTools.loadImageFromResource("resources/apple.png");

        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(4, 3));

        this.apples = new ArrayList<Point>();
        this.apples.add(getRandomGridLocation());
        this.apples.add(getRandomGridLocation());
        this.apples.add(getRandomGridLocation());
        this.apples.add(getRandomGridLocation());
        this.apples.add(getRandomGridLocation());

        this.goldenSnitches = new ArrayList<Point>();
        this.goldenSnitches.add(getRandomGridLocation());
        this.goldenSnitches = new ArrayList<Point>();
        this.goldenSnitches.add(getRandomGridLocation());

        this.blackSnitches = new ArrayList<Point>();
        this.blackSnitches.add(getRandomGridLocation());
        this.blackSnitches = new ArrayList<Point>();
        this.blackSnitches.add(getRandomGridLocation());

        this.poisonApples = new ArrayList<Point>();
        this.poisonApples.add(getRandomGridLocation());
        this.poisonApples.add(getRandomGridLocation());
        this.poisonApples.add(getRandomGridLocation());


//        this.grid.setColor(new Color(0, 0, 0, 0));
        this.grid.setColumns(42);
        this.grid.setRows(26);
        this.grid.setCellWidth(20);
        this.grid.setShowCellCoordinates(false);
        this.grid.setPosition(new Point(50, 50));

        this.wall = new ArrayList<Point>();
        for (int i = -1; i <= this.grid.getColumns(); i++) {
            this.wall.add(new Point(i, -1));
            this.wall.add(new Point(i, this.grid.getRows()));
        }

    }

    private Point getRandomGridLocation() {
        return new Point((int) (Math.random() * this.grid.getColumns()),
                (int) (Math.random() * this.grid.getColumns()));
    }

    @Override
    public void timerTaskHandler() {
        if (this.gameState == GameState.RUNNING) {
            if (snake != null) {
                System.out.println("have snake");
                if (delay <= 0) {
                    System.out.println("move");
                    snake.move();
                    moveBlackSnitch();
                    delay = defaultDelay;
                    if (snake.selfHitTest()) {
                        this.gameState = GameState.ENDED;
                    }
                    checkSnakeAppleIntersection();
                    checkSnakeGoldenSnitchesIntersection();
                    checkSnakeBlackSnitchesIntersection();
                    checkSnakePoisonAppleIntersection();

                    moveGoldenSnitch();

                } else {
                    System.out.println("decrement");
                    delay--;

                }
                if (snake.getDirection() == Direction.RIGHT) {
                    if (snake.getHead().x >= this.grid.getColumns()) {
                        snake.getHead().x = 0;
                    }
                } else if (snake.getDirection() == Direction.LEFT) {
                    if (snake.getHead().x <= -1) {
                        snake.getHead().x = this.grid.getColumns() - 1;
                    }
                } else if (snake.getDirection() == Direction.UP) {
                    if (snake.getHead().y <= -1) {
                        snake.getHead().y = this.grid.getColumns() - 1;
                        }
                } else if (snake.getDirection() == Direction.DOWN) {
                    if (snake.getHead().y >= this.grid.getColumns()) {
                        snake.getHead().y = 0;
                    
                    }
                }
            }
        }
    }

    private void moveGoldenSnitch() {
        for (Point location : goldenSnitches) {
            if (Math.random() > .9) {
                location.x += 1;
            } else {
                location.x -= 1;
            }
            if (Math.random() > .9) {
                location.y += 1;
            } else {
                location.y -= 1;
                this.defaultDelay = -40;
            }
        }
    }

    private void moveBlackSnitch() {
        for (int i = 0; i < blackSnitches.size(); i++) {

            if (Math.random() > .67) {

                if (snake.getHead().x > blackSnitches.get(i).x) {
                    blackSnitches.get(i).x++;
                }
                if (snake.getHead().y > blackSnitches.get(i).y) {
                    blackSnitches.get(i).y++;
                }
                if (snake.getHead().x < blackSnitches.get(i).x) {
                    blackSnitches.get(i).x--;
                }
                if (snake.getHead().y < blackSnitches.get(i).y) {
                    blackSnitches.get(i).y--;
                }
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //toggle the PAUSED/RUNNING state
            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;
            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
            }

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
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            snake.setGrowthCount(2);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = GameState.ENDED;
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

            if (this.apples != null) {
                for (int i = 0; i < this.apples.size(); i++) {
                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize());

                }
            }
            if (this.goldenSnitches != null) {
                for (int i = 0; i < this.goldenSnitches.size(); i++) {
                    graphics.drawImage(snitch, this.grid.getCellPosition(this.goldenSnitches.get(i)).x, this.grid.getCellPosition(this.goldenSnitches.get(i)).y, this.grid.getCellWidth(), this.grid.getCellHeight(), this);
                }
            }
            if (this.poisonBottles != null) {
                for (int i = 0; i < this.poisonBottles.size(); i++) {
                    GraphicsPalette.drawPoisonBottle(graphics, this.grid.getCellPosition(this.poisonBottles.get(i)), this.grid.getCellSize(), Color.BLACK);
                }
            }
            if (this.blackSnitches != null) {
                for (int i = 0; i < this.blackSnitches.size(); i++) {
                    graphics.drawImage(snitch1, this.grid.getCellPosition(this.blackSnitches.get(i)).x, this.grid.getCellPosition(this.blackSnitches.get(i)).y, this.grid.getCellWidth(), this.grid.getCellHeight(), this);
                }
            }
            if (this.poisonApples != null) {
                for (int i = 0; i < this.poisonApples.size(); i++) {
                    graphics.drawImage(apple1, this.grid.getCellPosition(this.poisonApples.get(i)).x, this.grid.getCellPosition(this.poisonApples.get(i)).y, this.grid.getCellWidth(), this.grid.getCellHeight(), this);
                }
            }

            if (this.wall != null) {
                for (int i = 0; i < this.wall.size(); i++) {
                    graphics.setColor(Color.RED);
                    graphics.fill3DRect(this.grid.getCellPosition(this.wall.get(i)).x, this.grid.getCellPosition(this.wall.get(i)).y, this.grid.getCellWidth(), this.grid.getCellHeight(), true);
                }

            }
            Point cellLocation;
            graphics.setColor(Color.BLACK);
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




        if (gameState == GameState.ENDED) {
            graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
            graphics.drawString("Game Over!!!", 175, 300);
        }
    }

    private void checkSnakeAppleIntersection() {
        for (int i = 0; i < this.apples.size(); i++) {
            if (snake.getHead().equals(this.apples.get(i))) {
                this.score += 10;
                this.snake.grow(1);
                this.apples.get(i).setLocation(getRandomGridLocation());

            }
        }
    }

    private void checkSnakeBlackSnitchesIntersection() {
        for (int i = 0; i < this.blackSnitches.size(); i++) {
            if (snake.getHead().equals(this.blackSnitches.get(i))) {
                this.score += -50;
                this.snake.grow(6);
                this.blackSnitches.get(i).setLocation(getRandomGridLocation());
            }
        }
    }

    private void checkSnakeGoldenSnitchesIntersection() {
        for (int i = 0; i < this.goldenSnitches.size(); i++) {
            if (snake.getHead().equals(this.goldenSnitches.get(i))) {
                this.score += 100;
                this.snake.grow(0);
                this.goldenSnitches.get(i).setLocation(getRandomGridLocation());
            }
        }
    }

    private void checkSnakePoisonAppleIntersection() {
        for (int i = 0; i < this.poisonApples.size(); i++) {
            if (snake.getHead().equals(this.poisonApples.get(i))) {
                this.score += -10;
                this.snake.grow(3);
                this.poisonApples.get(i).setLocation(getRandomGridLocation());

            }
        }
    }
}
