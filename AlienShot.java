package com.spaceinvaders;

import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class AlienShot {
    int x = 0;
    int y = 0;
    int anim_frame = 1;
    private static final int WIDTH = 9;
    private static final int HEIGHT = 21;
    private Game game;
    private Image spr_alienshot_1;
    private Image spr_alienshot_2;
    private Image spr_alienshot_3;
    private Image spr_alienshot_4;

    public AlienShot(Game game, int initx, int inity) {
        this.game = game;
        x = initx;
        y = inity;
        loadImage();
    }

    private void loadImage() {
        ImageIcon alienshot_1 = new ImageIcon("src/sprites/AlienShot_1.png");
        spr_alienshot_1 = alienshot_1.getImage();
        ImageIcon alienshot_2 = new ImageIcon("src/sprites/AlienShot_2.png");
        spr_alienshot_2 = alienshot_2.getImage();
        ImageIcon alienshot_3 = new ImageIcon("src/sprites/AlienShot_3.png");
        spr_alienshot_3 = alienshot_3.getImage();
        ImageIcon alienshot_4 = new ImageIcon("src/sprites/AlienShot_4.png");
        spr_alienshot_4 = alienshot_4.getImage();
    }

    void step() {
        y += 4;
        if ((game.gameclock % 4) == 0) {
            if (anim_frame != 4) {
                anim_frame += 1;
            }
            else {
                anim_frame = 1;
            }
        }
    }

    public void paint(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g;
        switch(anim_frame) {
            case 1:
                g2d.drawImage(spr_alienshot_1, x, y, null);
                break;
            case 2:
                g2d.drawImage(spr_alienshot_2, x, y, null);
                break;
            case 3:
                g2d.drawImage(spr_alienshot_3, x, y, null);
                break;
            case 4:
                g2d.drawImage(spr_alienshot_4, x, y, null);
                break;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
