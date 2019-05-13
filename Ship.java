package com.spaceinvaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Ship {
    //initialize variables
    private static final int WIDTH = 48;
    private static final int HEIGHT = 10;
    int x = 0;
    int y = 525;
    int nextx = 0;
    int xspd = 3;
    int shotcooldown = 0;
    boolean left_move;
    boolean right_move;
    boolean shooting;
    private Game game;
    private Image spr_ship;

    //constructor
    public Ship(Game game) {
        this.game = game;
        loadImage();
    }

    //loadImage() loads the ship's sprite into memory
    private void loadImage() {
        ImageIcon ship = new ImageIcon("src/sprites/ship.png");
        spr_ship = ship.getImage();
    }

    //step() executes every frame of the game
    public void step() {
        //check for input and react accordingly
        if (left_move && right_move) {
            nextx = 0;
        }
        else if (left_move) {
            nextx = -2;
        }
        else if (right_move) {
            nextx = 2;
        }
        else {
            nextx = 0;
        }
        if ((shooting) && (shotcooldown < 1)) {
            game.shipShoot(x-9, y);
            shotcooldown = 50;
        }
        //prevent player from going off the edge of the screen
        if (x + nextx > 0 && x + nextx < game.getWidth() - WIDTH)
            x = x + nextx;
        //reduce shotcooldown if neccesary
        if (shotcooldown > 0)
            shotcooldown -= 1;
    }

    //paint() draws the object to the screen
    public void paint(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(spr_ship, x, y, null);
    }

    //getBounds() is used for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    //changes variables based on when keys are released
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            left_move = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            right_move = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            shooting = false;
    }

    //changes variables based on when keys are pressed
    public void keyPressed(KeyEvent e) {
        switch (game.state) {
            case "playing":
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    left_move = true;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    right_move = true;
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    shooting = true;
                }
                break;
            case "game_over":
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    game.setupGame();
                    game.level = 1;
                }
                break;
            case "start_menu":
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    game.setupGame();
                }
                break;
        }
    }
}
