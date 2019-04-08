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
    private Game game;

    public Ship(Game game) {
        this.game = game;
    }

    public void step() {
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
        nextx = 0;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            nextx = -xspd;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            nextx = xspd;
        if ((e.getKeyCode() == KeyEvent.VK_SPACE) && (shotcooldown == 0))
            game.shipShoot(x,y);
            shotcooldown = 20;
    }
}
