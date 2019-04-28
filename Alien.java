package com.spaceinvaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import static java.lang.Math.abs;

public class Alien {
    private static final int DIAMETER = 30;

    int x = 0;
    int y = 0;
    int initial_x;
    int initial_y;
    int direction = 1;
    int xdisttraveled = 150;
    private Game game;

    public Alien(Game game, int initx, int inity) {
        this.game = game;
        x = initx;
        y = inity;
        initial_x = x;
        initial_y = y;
    }

    void step() {
        //if (game.state == "playing") {
            x += (game.alienSpeed * direction);
            xdisttraveled += game.alienSpeed;
            if (xdisttraveled > 300) {
                xdisttraveled = 0;
                direction *= -1;
                y += 30;
            //}
        }
    }

    public void paint(Graphics2D g) {
        g.fillOval(x, y, DIAMETER, DIAMETER);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }
}
