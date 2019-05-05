package com.spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

    //Initialize Variables, Array Lists, and Iterators
    Ship ship = new Ship(this);
    //The ArrayLists for Aliens and ShipShots hold all of those objects currently created, which makes it convenient
    //to iterate through them.
    ArrayList<Alien> alienArray = new ArrayList<Alien>();
    ArrayList<ShipShot> shipShotArray = new ArrayList<ShipShot>();
    ArrayList<Alien> alienToBeDeleted = new ArrayList<Alien>();
    ArrayList<ShipShot> shipShotToBeDeleted = new ArrayList<ShipShot>();
    int alienSpeed = 1;
    int score = 0;
    int level = 1;
    int timer = -1;
    int lives = 3;
    int gameclock = 0;
    String state = "start_menu";
    private String highScore = "";

    //This method returns the score
    private int getScore() {
        return score;
    }

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
    }

    //this method creates a ship shot object and adds it to the shipShotArray
    void shipShoot(int x, int y) {
        shipShotArray.add(new ShipShot(this, x, y));
    }

    //this method spawns all aliens in their starting positions
    private void spawnAliens() {
        for (int i = 100; i < 401; i += 40) {
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

    private void incrementGameClock() {
        if (gameclock < 999999999)
            gameclock += 1;
        else
            gameclock = 0;
    }

    //this method calls the "step" method of every object in the game. The step method is a method that executes every
    //single "frame" of the game.
    private void step() {
        for (Alien a : this.alienArray) {
            a.step();
        }
        for (ShipShot b : this.shipShotArray) {
            b.step();
        }
        ship.step();
    }

    private void resetAlienPositions() {
        for (Alien a : this.alienArray) {
            a.x = a.initial_x;
            a.y = a.initial_y;
        }
    }
    //This method checks for collisions between different objects
    private void checkCollisions() {
        //This part of the code checks for collisions between all of the ShipShot objects and all of the Alien objects
        for (ShipShot b : this.shipShotArray) {
            for (Alien a : this.alienArray) {
                if (a.getBounds().intersects(b.getBounds())) {
                    //because Java gets mad when we try to delete an item from a list we are currently iterating through,
                    //we will instead add all colliding shipShots and Aliens into trash arrays, and we will delete them
                    //later
                    alienToBeDeleted.add(a);
                    shipShotToBeDeleted.add(b);
                    score += (a.score/2);
                }
            }
        }
        //This part of the code checks for collisions between the ship and all of the alien objects. If there is a
        //collision, the game ends.
        for (Alien a : this.alienArray) {
            if (a.getBounds().intersects(ship.getBounds())) {
                //This part of the code clears all of the aliens, ship shots, and the player object from the screen
                for (ShipShot c : this.shipShotArray) {
                    shipShotToBeDeleted.add(c);
                }
                if (lives > 0) {
                    if (state == "playing") {
                        lives -= 1;
                        state = "try_again";
                        timer = 60;
                    }
                }
                if (lives == 0) {
                    state = "game_over";
                    lives = 3;
                    for (Alien b : this.alienArray) {
                        alienToBeDeleted.add(b);
                    }
                }
                //Put score code here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                /*
                public String GetHighScore()
                {
                    //format:  Sam:100
                    FileReader readFile = null;
                    BufferedReader reader = null;
                    try
                    {
                        readFile = new FileReader("highscore.dat");
                        reader = new BufferReader(readFile);
                        return reader.readLine();
                    }
                    catch (Exception e)
                    {
                        return "0";
                    }
                    finally
                    {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } */
            }
            //This part of the code removes objects we couldn't safely remove before from our "To Be Deleted" Lists
        }
        alienArray.removeAll(alienToBeDeleted);
        shipShotArray.removeAll(shipShotToBeDeleted);
    }

    //This method checks how many Aliens are remaining, and adjusts the alienSpeed variable accordingly
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

    //This method draws all of the objects in the game, as well as GUI elements like score
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.BLACK);
        switch (state) {
            case "playing":
                for (Alien a : this.alienArray) {
                    a.paint(g2d);
                }
                for (ShipShot b : this.shipShotArray) {
                    b.paint(g2d);
                }
                ship.paint(g2d);
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font("Verdana", Font.BOLD, 30));
                g2d.drawString("Lives: " + Integer.toString(lives), 30, 40);
                g2d.drawString("Score: " + Integer.toString(score), 300, 40);
                break;
            case "try_again":
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font("Verdana", Font.BOLD, 60));
                g2d.drawString("Try Again", 30, 300);
                if (timer > 0) {
                   timer -= 1;
                }
                else {
                    resetAlienPositions();
                    state = "playing";
                }
                break;
            case "game_over":
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font("Verdana", Font.BOLD, 60));
                g2d.drawString("GAME OVER", 30, 300);
                g2d.setFont(new Font("Verdana", Font.BOLD, 40));
                g2d.drawString("Press SPACE to try again", 30, 400);
                break;
            case "start_menu":
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font("Verdana", Font.BOLD, 60));
                g2d.drawString("SPACE INVADERS", 30, 250);
                g2d.setFont(new Font("Verdana", Font.BOLD, 20));
                g2d.drawString("By: Daniel Scott and Miranda Thompson", 30, 300);
                g2d.drawString("Press SPACE to begin", 30, 400);
                break;
            }
        }

        //This method brings up a text box which tells the player they got a game over
        public void gameOver () {
            JOptionPane.showMessageDialog(this, "your score is: " + getScore(),
                    "Game Over", JOptionPane.YES_NO_OPTION);
            System.exit(ABORT);
        }

        public void setupGame () {
        score = 0;
        state = "playing";
        spawnAliens();
        }

        //the main method
        public static void main (String[]args) throws InterruptedException {
            //creates the window for the game, as well as a new Game object
            JFrame frame = new JFrame("Space Invaders");
            Game game = new Game();
            frame.add(game);
            frame.setSize(600, 600);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //this is the main loop, which will repeat for as long as the game is running
            while (true) {
                 game.step();
                 game.repaint();
                 game.checkAlienCount();
                 if (game.state == "playing")
                     game.checkCollisions();
                 game.incrementGameClock();
                 Thread.sleep(10);
            }
        }
    }
