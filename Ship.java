package com.spaceinvaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Ship {
    private static final int WIDTH = 60;
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

    public Ship(Game game) {
        this.game = game;
    }

    public void step() {
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
            game.shipShoot(x, y);
            shotcooldown = 20;
        }
        if (x + nextx > 0 && x + nextx < game.getWidth() - WIDTH)
            x = x + nextx;
        if (shotcooldown > 0)
            shotcooldown -= 1;
    }

    public void paint(Graphics2D g) {
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            left_move = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            right_move = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            shooting = false;
    }

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
