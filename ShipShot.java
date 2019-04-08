package com.spaceinvaders;

import java.awt.*;

public class ShipShot {
    int x = 0;
    int y = 0;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private Game game;

    public ShipShot(Game game, int initx, int inity) {
        this.game = game;
        x = initx + 30;
        y = inity;
    }

    void step() {
        y -= 4;
    }

    public void paint(Graphics2D g) {
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
