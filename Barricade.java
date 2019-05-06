package com.spaceinvaders;

import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Barricade {
    int x = 0;
    int y = 0;
    private static final int WIDTH = 9;
    private static final int HEIGHT = 21;
    private Game game;
    private Image spr_barricade;

    public Barricade(Game game, int initx, int inity) {
        this.game = game;
        x = initx;
        y = inity;
        loadImage();
    }

    private void loadImage() {
        ImageIcon barricade = new ImageIcon("src/sprites/Barricade.png");
        spr_barricade = barricade.getImage();
    }

    public void paint(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(spr_barricade, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
