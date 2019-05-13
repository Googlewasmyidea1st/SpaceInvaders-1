package com.spaceinvaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Alien {
    private static final int DIAMETER = 30;

    int x = 0;
    int y = 0;
    int initial_x;
    int initial_y;
    int direction = 1;
    int xdisttraveled = 150;
    int score;
    int anim_frame = 1;
    private Game game;
    private Image spr_alien1_1;
    private Image spr_alien1_2;
    private Image spr_alien2_1;
    private Image spr_alien2_2;
    private Image spr_alien3_1;
    private Image spr_alien3_2;

    //constructor
    public Alien(Game game, int initx, int inity, int initscore) {
        this.game = game;
        x = initx;
        y = inity;
        initial_x = x;
        initial_y = y;
        score = initscore;
        loadImage();
    }

    //loadImage() loads the alien's sprites into memory
    private void loadImage() {
        ImageIcon alien1_1 = new ImageIcon("src/sprites/alien1_1.png");
        spr_alien1_1 = alien1_1.getImage();
        ImageIcon alien1_2 = new ImageIcon("src/sprites/alien1_2.png");
        spr_alien1_2 = alien1_2.getImage();
        ImageIcon alien2_1 = new ImageIcon("src/sprites/alien2_1.png");
        spr_alien2_1 = alien2_1.getImage();
        ImageIcon alien2_2 = new ImageIcon("src/sprites/alien2_2.png");
        spr_alien2_2 = alien2_2.getImage();
        ImageIcon alien3_1 = new ImageIcon("src/sprites/alien3_1.png");
        spr_alien3_1 = alien3_1.getImage();
        ImageIcon alien3_2 = new ImageIcon("src/sprites/alien3_2.png");
        spr_alien3_2 = alien3_2.getImage();
    }

    //step() executes every frame of the game
    void step() {
        //every 16 frames, change sprite
        if ((game.gameclock % 16) == 0) {
            if (anim_frame == 1) {
                anim_frame = 2;
            }
            else {
                anim_frame = 1;
            }
        }
        //every 4 frames, move
        if ((game.gameclock % 4) == 0) {
            x += (game.alienSpeed * direction);
            xdisttraveled += game.alienSpeed;
            //after moving a certain horizontal distance, move down and change horizontal direction
            if (xdisttraveled > 300) {
                xdisttraveled = 0;
                direction *= -1;
                y += 30;
            }
        }
    }

    //paint() draws the alien to the screen
    public void paint(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g;
        switch (score) {
            case 10:
                if (anim_frame == 1) {
                    g2d.drawImage(spr_alien1_1, x, y, null);
                } else {
                    g2d.drawImage(spr_alien1_2, x, y, null);
                }
                break;
            case 20:
                if (anim_frame == 1) {
                    g2d.drawImage(spr_alien2_1, x, y, null);
                } else {
                    g2d.drawImage(spr_alien2_2, x, y, null);
                }
                break;
            case 30:
                if (anim_frame == 1) {
                    g2d.drawImage(spr_alien3_1, x, y, null);
                } else {
                    g2d.drawImage(spr_alien3_2, x, y, null);
                }
                break;
        }
    }

    //getBounds() is used for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }
}
