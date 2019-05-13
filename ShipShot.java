package com.spaceinvaders;

import java.awt.*;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ShipShot {
    //initialize variables
    int x = 0;
    int y = 0;
    int anim_frame = 1;
    private static final int WIDTH = 9;
    private static final int HEIGHT = 21;
    private Game game;
    private Image spr_shipshot_1;
    private Image spr_shipshot_2;
    private Image spr_shipshot_3;
    private Image spr_shipshot_4;

    //constructor
    public ShipShot(Game game, int initx, int inity) {
        this.game = game;
        x = initx + 30;
        y = inity;
        loadImage();
    }

    //loadImage() loads shipShots sprites to memory
    private void loadImage() {
        ImageIcon shipshot_1 = new ImageIcon("src/sprites/ShipShot_1.png");
        spr_shipshot_1 = shipshot_1.getImage();
        ImageIcon shipshot_2 = new ImageIcon("src/sprites/ShipShot_2.png");
        spr_shipshot_2 = shipshot_2.getImage();
        ImageIcon shipshot_3 = new ImageIcon("src/sprites/ShipShot_3.png");
        spr_shipshot_3 = shipshot_3.getImage();
        ImageIcon shipshot_4 = new ImageIcon("src/sprites/ShipShot_4.png");
        spr_shipshot_4 = shipshot_4.getImage();
    }

    //step() executes every frame, and moves the shipShot upwards as well as handling animation
    void step() {
        y -= 6;
        if ((game.gameclock % 4) == 0) {
            if (anim_frame != 4) {
                anim_frame += 1;
            }
            else {
                anim_frame = 1;
            }
        }
    }

    //paint() draws shipShot to the screen
    public void paint(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g;
        switch(anim_frame) {
            case 1:
                g2d.drawImage(spr_shipshot_1, x, y, null);
                break;
            case 2:
                g2d.drawImage(spr_shipshot_2, x, y, null);
                break;
            case 3:
                g2d.drawImage(spr_shipshot_3, x, y, null);
                break;
            case 4:
                g2d.drawImage(spr_shipshot_4, x, y, null);
                break;
        }
    }

    //getBounds() is used for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
