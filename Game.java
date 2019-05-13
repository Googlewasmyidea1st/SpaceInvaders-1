package com.spaceinvaders;

//Importing Libraries
import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Game extends JPanel {
    //Initialize game variables and array lists
    Ship ship = new Ship(this);
    int alienSpeed = 1;
    int score = 0;
    int level = 1;
    int timer = -1;
    int lives = 3;
    int gameclock = 0;
    String state = "start_menu";
    int high_score = 0;
    int background_music = 0;
    private Image spr_startmenualien;

    ArrayList<Alien> alienArray = new ArrayList<Alien>();
    ArrayList<ShipShot> shipShotArray = new ArrayList<ShipShot>();
    ArrayList<AlienShot> alienShotArray = new ArrayList<AlienShot>();
    ArrayList<Barricade> barricadeArray = new ArrayList<Barricade>();
    ArrayList<Alien> alienToBeDeleted = new ArrayList<Alien>();
    ArrayList<ShipShot> shipShotToBeDeleted = new ArrayList<ShipShot>();
    ArrayList<AlienShot> alienShotToBeDeleted = new ArrayList<AlienShot>();
    ArrayList<Barricade> barricadeToBeDeleted = new ArrayList<Barricade>();

    //Game Constructor
    public Game() {
        //Adds the key listener, which is used for input
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                ship.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                ship.keyPressed(e);
            }
        });
        setFocusable(true);
        loadImage();
        //Loads the font from the fonts folder and registers it on the computer.
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/agency_fb_bold.ttf")));
        } catch (IOException |FontFormatException e) {
            //Handle exception
        }
    }

    //loadImage() loads a large png of a space invader which is displayed on the start menu
    private void loadImage() {
        ImageIcon startmenualien = new ImageIcon("src/sprites/StartMenuAlien.png");
        ImageIcon colorfulaliens = new ImageIcon("src/sprites/ColorfulAliens.png");
        ImageIcon tryagainalien = new ImageIcon("src/sprites/TryAgainAlien.png");
        spr_startmenualien = startmenualien.getImage();
        spr_colorfulaliens = colorfulaliens.getImage();
        spr_tryagainalien = tryagainalien.getImage();
    }

    //shipShoot() creates a ship shot object and adds it to the shipShotArray, this method is called from the Ship
    //object.
    void shipShoot(int x, int y) {
        shipShotArray.add(new ShipShot(this, x, y));
        Sound sfx_shipShoot = TinySound.loadSound("sounds/shoot.wav");
        sfx_shipShoot.play();
    }

    //alienShoot() creates an alienShot object at the position of a random existing alien
    public void alienShoot() {
        if (alienArray.size() > 0) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, alienArray.size());
            int xpos = alienArray.get(randomNum).x;
            int ypos = alienArray.get(randomNum).y;
            alienShotArray.add(new AlienShot(this, xpos, ypos));
        }
    }

    //spawnBarricades() is called at gameSetup(). It creates a large amount of barricade objects.
    private void spawnBarricades() {
        for (int add = 30; add < 481; add += 150) {
            for (int row1 = 20; row1 < 41; row1 += 10) {
                barricadeArray.add(new Barricade(this, (row1 + add), 450));
            }
            for (int row2 = 10; row2 < 51; row2 += 10) {
                barricadeArray.add(new Barricade(this, (row2 + add), 460));
            }
            for (int row3 = 0; row3 < 61; row3 += 10) {
                barricadeArray.add(new Barricade(this, (row3 + add), 470));
            }
            for (int row4 = 0; row4 < 61; row4 += 10) {
                if (row4 != 30) {
                    barricadeArray.add(new Barricade(this, (row4 + add), 480));
                }
            }
            for (int row5 = 0; row5 < 61; row5 += 10) {
                if (!(19 < row5 && row5 < 41)) {
                    barricadeArray.add(new Barricade(this, (row5 + add), 490));
                }
            }
        }

    }
    //spawnAliens() is called at gameSetup() and at the beginning of each level. It spawns all of the aliens at their
    //starting positions
    private void spawnAliens() {
        for (int i = 150; i < 401; i += 40) {
            for (int ii = 40; ii < 201; ii += 40) {
                switch (ii) {
                    case 40:
                        alienArray.add(new Alien(this, i, ii, 30));
                        break;
                    case 80:
                        alienArray.add(new Alien(this, i, ii, 20));
                        break;
                    case 120:
                        alienArray.add(new Alien(this, i, ii, 20));
                        break;
                    case 160:
                        alienArray.add(new Alien(this, i, ii, 10));
                        break;
                    case 200:
                        alienArray.add(new Alien(this, i, ii, 10));
                        break;
                }
            }
        }
    }

    //incrementGameClock() increments the gameclock variable by 1. The gameclock variable is used for several things,
    //such as telling the aliens when it is time to move, and when it is time to shoot.
    private void incrementGameClock() {
        if (gameclock < 999999999)
            gameclock += 1;
        else
            gameclock = 0;
    }

    //step() calls the "step" method of every object in the game. The step method is a method that executes every
    //single "frame" of the game.
    private void step() {
        for (Alien b : this.alienArray) {
            b.step();
        }
        for (ShipShot c : this.shipShotArray) {
            c.step();
        }
        for (AlienShot d : this.alienShotArray) {
            d.step();
        }
        ship.step();
    }

    //resetAlienPositions() returns all aliens to their starting positions. This is called when you die and the game
    //needs to put surviving aliens back at the top of the screen without respawning dead aliens.
    private void resetAlienPositions() {
        for (Alien a : this.alienArray) {
            a.x = a.initial_x;
            a.y = a.initial_y;
            a.xdisttraveled = 150;
        }
    }
    //checkCollisions() checks for collisions between different objects
    private void checkCollisions() {
        if (state == "playing") {
            //This part of the code checks for collisions between all of the ShipShot objects and all of the Alien objects
            for (ShipShot b : this.shipShotArray) {
                for (Alien a : this.alienArray) {
                    //COLLISIONS BETWEEN: ShipShots and Aliens
                    if (a.getBounds().intersects(b.getBounds())) {
                        //because Java gets mad when we try to delete an item from a list we are currently iterating through,
                        //we will instead add all colliding shipShots and Aliens into trash arrays, and we will delete them
                        //later
                        alienToBeDeleted.add(a);
                        shipShotToBeDeleted.add(b);
                        score += (a.score / 2);
                        Sound sfx_alienExplosion = TinySound.loadSound("sounds/invaderKilled.wav");
                        sfx_alienExplosion.play();
                    }
                }
                for (Barricade d : this.barricadeArray) {
                    //COLLISIONS BETWEEN: ShipShots and Barricades
                    if (d.getBounds().intersects(b.getBounds())) {
                        //because Java gets mad when we try to delete an item from a list we are currently iterating through,
                        //we will instead add all colliding shipShots and Aliens into trash arrays, and we will delete them
                        //later
                        barricadeToBeDeleted.add(d);
                        shipShotToBeDeleted.add(b);
                    }
                }
            }
            for (AlienShot c : this.alienShotArray) {
                //COLLISIONS BETWEEN: AlienShots and the player's ship
                if (c.getBounds().intersects(ship.getBounds())) {
                    death();
                }
                for (Barricade d : this.barricadeArray) {
                    //COLLISIONS BETWEEN: AlienShots and Barricades
                    if (d.getBounds().intersects(c.getBounds())) {
                        //because Java gets mad when we try to delete an item from a list we are currently iterating through,
                        //we will instead add all colliding shipShots and Aliens into trash arrays, and we will delete them
                        //later
                        barricadeToBeDeleted.add(d);
                        alienShotToBeDeleted.add(c);
                    }
                }
            }
            //This code checks the y variables of all aliens, and triggers the death() method if an alien gets too low
            for (Alien a : this.alienArray) {
                if (a.y > 500) {
                    death();
                }
            }
            //This part of the code removes objects we couldn't safely remove before from our "To Be Deleted" Lists
            alienArray.removeAll(alienToBeDeleted);
            shipShotArray.removeAll(shipShotToBeDeleted);
            alienShotArray.removeAll(alienShotToBeDeleted);
            barricadeArray.removeAll(barricadeToBeDeleted);
        }
    }

    //death() is called whenever an event happens that results in the player losing a life
    private void death() {
        //Clear out projectiles before going back into the game
        for (ShipShot c : this.shipShotArray) {
            shipShotToBeDeleted.add(c);
        }
        for (AlienShot d : this.alienShotArray) {
            alienShotToBeDeleted.add(d);
        }
        Sound sfx_shipExplosion = TinySound.loadSound("sounds/explosion.wav");
        sfx_shipExplosion.play();
        //This code decrements life count if you still have lives remaining
        if (lives > 0) {
            if (state == "playing") {
                lives -= 1;
                state = "try_again";
                timer = 60;
            }
        }
        //This code handles game overs if you have no lives remaining
        if (lives == 0) {
            state = "game_over";
            lives = 3;
            for (Alien b : this.alienArray) {
                alienToBeDeleted.add(b);
                //Writes a new high score to the highscore.txt file, if your score is higher than the current high score
                if (score > high_score)
                    try {
                        FileWriter fw = new FileWriter("src/text/highscore.txt", false);
                        fw.write(String.valueOf(score));
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    //checkAlienCount() checks how many Aliens are remaining, and adjusts the alienSpeed variable accordingly
    private void checkAlienCount() {
        int alienCount = alienArray.size();
        if ((alienCount == 0) && (state == "playing")) {
            level += 1;
            spawnAliens();
        }
        if (alienCount < 15) {
            alienSpeed = 3 + (level - 1);
        }
        else if (alienCount < 30) {
            alienSpeed = 2 + (level - 1);
        }
        else {
            alienSpeed = 1 + (level - 1);
        }
    }

    //paint() draws all of the objects in the game, as well as GUI elements like score
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.BLACK);
        switch (state) {
            case "playing":
                //draw all game objects to the frame
                for (Alien a : this.alienArray) {
                    a.paint(g2d);
                }
                for (ShipShot b : this.shipShotArray) {
                    b.paint(g2d);
                }
                for (AlienShot c : this.alienShotArray) {
                    c.paint(g2d);
                }
                for (Barricade d : this.barricadeArray) {
                    d.paint(g2d);
                }
                ship.paint(g2d);
                //draw all GUI elements to the frame
                 g2d.setColor(Color.CYAN);
                g2d.setFont(new Font("Agency FB Bold", Font.BOLD, 30));
                g2d.drawString("Lives: " + Integer.toString(lives), 20, 40);
                g2d.drawString("Score: " + Integer.toString(score), 470, 40);
                g2d.drawString("High Score: " + Integer.toString(high_score), 235, 40);
                break;
            case "try_again":
                g2d.setColor(Color.YELLOW);
                g2d.setFont(new Font("Agency FB Bold", Font.BOLD, 100));
                g2d.drawString("TRY AGAIN!", 110, 200);
                g2d.drawImage(spr_tryagainalien, 100, 300, null);
                //after 60 ms, reset game state back to "playing"
                if (timer > 0) {
                    timer -= 1;
                } else {
                    resetAlienPositions();
                    state = "playing";
                }
                break;
            case "game_over":
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Agency FB Bold", Font.BOLD, 120));
                g2d.drawString("GAME OVER", 60, 250);
                g2d.setFont(new Font("Agency FB Bold", Font.BOLD, 40));
                g2d.drawString("Press SPACE to try again", 120, 350);
                g2d.drawImage(spr_colorfulaliens, 100, 400, null);
                break;
            case "start_menu":
                g2d.setColor(Color.GREEN);
                g2d.setFont(new Font("Agency FB Bold", Font.BOLD, 90));
                g2d.drawString("SPACE INVADERS", 30, 250);
                g2d.setFont(new Font("Agency FB Bold", Font.BOLD, 30));
                g2d.drawString("By: Daniel Scott and Miranda Thompson", 100, 300);
                g2d.setFont(new Font("Agency FB Bold", Font.BOLD, 35));
                g2d.drawString("Press SPACE to begin", 150, 400);
                g2d.drawImage(spr_startmenualien, 180, 400, null);
                break;
        }
    }

    //setupGame() runs at the beginning of every new game. It handles several different tasks.
    public void setupGame () {
    score = 0;
    state = "playing";
    //reads in high score from highscore.txt file
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/text/highscore.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        high_score = scanner.nextInt();
        scanner.close();
    spawnAliens();
    spawnBarricades();
    }

    //the main method
    public static void main (String[]args) throws InterruptedException {
        //creates the window for the game, as well as a new Game object
        JFrame frame = new JFrame("Space Invaders");
        Game game = new Game();
        frame.add(game);
        frame.setSize(600, 650);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //opens TinySound library for use, starts background music
        TinySound.init();
        Music bgm = TinySound.loadMusic("sounds/bgm.wav");
        bgm.play(true);
        //this is the main loop, which will repeat for as long as the game is running
        while (true) {
             //call all objects "step" methods
             game.step();
             //draw all objects to the screen
             game.repaint();
             if (game.state == "playing") {
                 //check how many aliens are remaining, and adjust speed accordingly
                 game.checkAlienCount();
                 //check for collisions between all objects
                 game.checkCollisions();
                 //every 100 frames, create an alienShot
                 if ((game.gameclock % 100) == 0) {
                     game.alienShoot();
                 }
                 //increment the game clock
                 game.incrementGameClock();
             }
             Thread.sleep(10);
        }
    }
}
