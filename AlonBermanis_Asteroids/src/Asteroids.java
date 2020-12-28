/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.ArrayList;
import java.applet.AudioClip;

/**
 *
 * @author RealProgramming4Kids
 */
public class Asteroids extends Applet implements KeyListener, ActionListener{
    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    Image offscreen;
    Graphics offG;
    SpaceCraft ship;
    ArrayList<Asteroid> asteroidList;
    ArrayList<Bullet> bulletList;
    ArrayList<Debris> debrisList;
    ArrayList<Ammo> ammoList;
    Timer timer;
    boolean upKey, leftKey, rightKey, spaceKey, enterKey, qKey;
    boolean isPaused;
    int score;
    AudioClip laser, shipHit, asteroidHit, thruster;
    float sizeDecrease = 0.6f;
    float startingSize = 1.2f;
    int asteroidNum;
    boolean mainMenu = true;
    int bulletCounter;
    int bulletSet = 10;
    int ammoSpawnCounter;
    int ammoSpawnSet = 10;
    boolean gameOver = false;
    int ammoAmountSet = 10;
    
    public void init() {
        // TODO start asynchronous download of heavy resources
        //asteroidNum = (int) Math.round(Math.random() * 10) + 5;
        
        this.setSize(900,650);
        this.addKeyListener(this);
        
        timer = new Timer(20,this);
        offscreen = createImage(this.getWidth(), this.getHeight());
        offG = offscreen.getGraphics();
    }
    
    public void start(){
        timer.start();
    }
    
    public void stop(){
        timer.stop();
    }
    
    public void actionPerformed(ActionEvent e){
        if(!mainMenu){
            respawnShip();
            keyCheck();
            ship.updatePosition();
            for(int i = 0; i < asteroidList.size(); i++){
                //asteroidList.get(i).prevX = asteroidList.get(i).xPosition;
                //asteroidList.get(i).prevY = asteroidList.get(i).yPosition;
                asteroidList.get(i).updatePosition();
            }
            for(int i = 0; i < bulletList.size(); i++){
                bulletList.get(i).updatePosition();
                if (bulletList.get(i).counter > 60 || !bulletList.get(i).active){
                    bulletList.remove(i);
                }
            }
            for(int i = 0; i < debrisList.size(); i++){
                debrisList.get(i).updatePosition();
                if (debrisList.get(i).counter > 30){
                    debrisList.remove(i);
                }
            }
            for(int i = 0; i < ammoList.size(); i++){
                ammoList.get(i).updatePosition();
            }
            checkCollision();
            checkAsteroidDestruction();
            checkAddAmmo();
        }
        else{
            keyCheck();
        }
        
    }
    
    public void paint(Graphics g) {
        offG.setColor(Color.black);
        offG.fillRect(0, 0, 900, 600);
        offG.setColor(Color.white);
        offG.fillRect(0, 600, 900, 650);
        
        if(!mainMenu){           
            ship.updatePosition();
            
            if (ship.active){
               ship.paint(offG); 
            }
            for(int i = 0; i < asteroidList.size(); i++){
                asteroidList.get(i).paint(offG);
            }
            for(int i = 0; i < bulletList.size(); i++){
                bulletList.get(i).paint(offG);
            }
            for(int i = 0; i < debrisList.size(); i++){
                debrisList.get(i).paint(offG);
            }
            for(int i = 0; i < ammoList.size(); i++){
                offG.setColor(Color.red);
                ammoList.get(i).paint(offG);
                offG.setColor(Color.white);
            }

            if(ship.livesCounter == 0){
                offG.drawString("YOU LOSE!", 400, 300);
                offG.drawString("Press ENTER to restart", 400, 350);
                offG.drawString("Press Q to exit the game", 400, 400);
                for(int i = 0; i < asteroidList.size(); i++){
                    asteroidList.remove(i);
                }
                gameOver = true;
                for(int i = 0; i < ammoList.size(); i++){
                    ammoList.remove(i);
                }
            }
            else if(asteroidList.isEmpty()){
                offG.drawString("YOU WIN!", 400, 300);
                offG.drawString("Press ENTER to restart", 400, 350);
                offG.drawString("Press Q to exit the game", 400, 400);
                ship.active = false;
                ship.livesCounter = -1;
                gameOver = true;
                for(int i = 0; i < ammoList.size(); i++){
                    ammoList.remove(i);
                }
            }
            offG.setColor(Color.white);
            offG.fillRect(0, 600, 900, 650);
            offG.setColor(Color.black);
            offG.drawString("lives " + ship.livesCounter, 5, 650);
            offG.drawString("Score " + score, 830, 650);
            offG.drawString("Asteroids " + asteroidList.size(), 500, 650);
            offG.drawString("Bullets " + bulletCounter, 700, 650);
            offG.drawString("Press Q to exit", 700, 610);
            offG.drawString("Press P to pause/resume", 300, 610);
        }
        else{
            offG.drawString("Press ENTER to start the game", 400, 300);
            offG.drawString("Press Q to exit the game", 400, 350);
                      
        }
        
        g.drawImage(offscreen, 0, 0, this);
        repaint();
    }
    
    public void update(Graphics g){
        paint(g);
    }
    
    public void keyPressed(KeyEvent e){
        if(!mainMenu){
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                rightKey = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                leftKey = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP && ship.active){
                upKey = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN){

            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE){
                spaceKey = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_P && isPaused){
                start();
                isPaused = false;
            }
            else if(e.getKeyCode() == KeyEvent.VK_P && !isPaused){
                stop();
                isPaused = true;
            }
        }      
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            enterKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q){
                qKey = true;
        }
    }
    
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            rightKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            leftKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP){
            upKey = false;
            thruster.stop();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            spaceKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            enterKey = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_Q){
            qKey = false;
        }
    }
    
    public void keyTyped(KeyEvent e){
        
    }
    
    public void keyCheck(){
        if (leftKey){ // == true
            ship.rotateLeft();
        }
        
        if (rightKey){ // == true
            ship.rotateRight();
        }
        
        if (upKey){ // == true
            ship.accelarate();
            thruster.loop();
        }
        else if(!mainMenu && !upKey){ // == false
            ship.stop();
        }
        
        if (spaceKey){
            fire();
        }
        
        if(enterKey && mainMenu){
            startGame();
            mainMenu = false;
        }
        else if(enterKey && gameOver){
            restartGame();
        }
        
        if (mainMenu){
            if (qKey){
                System.out.println("exit");
                System.exit(0);
            }
        }
        else{
            if (qKey && asteroidList.isEmpty()){
                System.out.println("exit1");
                System.exit(0);
            }
        }
    }
    
    public boolean collision(VectorSprite a, VectorSprite b){
        int x, y;
        for(int i = 0; i < a.drawShape.npoints; i++){
            x = a.drawShape.xpoints[i];
            y = a.drawShape.ypoints[i];
            if (b.drawShape.contains(x, y)){
                return true;
            }
        }
        for(int i = 0; i < b.drawShape.npoints; i++){
            x = b.drawShape.xpoints[i];
            y = b.drawShape.ypoints[i];
            if (a.drawShape.contains(x, y)){
                return true;
            }
        }
        return false;
    }
    
    public void checkCollision(){
        for(int i = 0; i < asteroidList.size(); i++){
            if (collision(ship, asteroidList.get(i)) == true && ship.active){
                ship.hit();
                score -= 20;
                shipHit.play();
            }
            for (int j = 0; j < bulletList.size(); j++){
                if (collision(bulletList.get(j), asteroidList.get(i)) == true){
                    bulletList.get(j).active = false;
                    asteroidList.get(i).active = false;
                    for(int g = 0; g < Math.random() * 10 + 2; g++){
                        debrisList.add(new Debris(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition));
                    }
                    asteroidHit.play();
                }
            }
            for (int j = 0; j < asteroidList.size(); j++){
                if(i == j && j != asteroidList.size() - 1){
                    j++;
                }
                if(i != j){      
                    //double prevX1, prevY1, prevX2, prevY2;
                    //prevX1 = asteroidList.get(i).xPosition;
                    //prevY1 = asteroidList.get(i).yPosition;
                    //prevX2 = asteroidList.get(j).xPosition;
                    //prevY2 = asteroidList.get(j).yPosition;
                    if (collision(asteroidList.get(i), asteroidList.get(j)) && asteroidList.get(i).active && asteroidList.get(j).active){                       
                        bounce(asteroidList.get(i), asteroidList.get(j)/*, asteroidList.get(i).prevX, asteroidList.get(i).prevY, asteroidList.get(j).prevX, asteroidList.get(j).prevY*/);
                        //bounce(asteroidList.get(j), asteroidList.get(j).prevX, asteroidList.get(j).prevY);
                        asteroidList.get(i).updatePosition();
                        asteroidList.get(j).updatePosition();
                        asteroidList.get(i).rotation = Math.random()/5 - 0.1;
                        asteroidList.get(j).rotation = Math.random()/5 - 0.1;
                        asteroidList.get(i).updatePosition();
                        asteroidList.get(j).updatePosition();
                    }
                }
            }
            
            for (int j = 0; j < ammoList.size(); j++){
                if (collision(asteroidList.get(i), ammoList.get(j)) && asteroidList.get(i).active && ammoList.get(j).active){                       
                    bounce(asteroidList.get(i), ammoList.get(j)/*, asteroidList.get(i).prevX, asteroidList.get(i).prevY, asteroidList.get(j).prevX, asteroidList.get(j).prevY*/);
                    //bounce(asteroidList.get(j), asteroidList.get(j).prevX, asteroidList.get(j).prevY);
                    asteroidList.get(i).updatePosition();
                    ammoList.get(j).updatePosition();
                    asteroidList.get(i).rotation = Math.random()/5 - 0.1;
                    ammoList.get(j).rotation = Math.random()/5 - 0.1;
                    asteroidList.get(i).updatePosition();
                    ammoList.get(j).updatePosition();
                }
            }
            
            
        } 
        for (int i = 0; i < ammoList.size(); i++){
            for (int j = 0; j < ammoList.size(); j++){
                if(i == j && j != ammoList.size() - 1){
                    j++;
                }
                if(i != j){
                    if (collision(ammoList.get(i), ammoList.get(j)) && ammoList.get(i).active && ammoList.get(j).active){                       
                        bounce(ammoList.get(i), ammoList.get(j)/*, asteroidList.get(i).prevX, asteroidList.get(i).prevY, asteroidList.get(j).prevX, asteroidList.get(j).prevY*/);
                        //bounce(asteroidList.get(j), asteroidList.get(j).prevX, asteroidList.get(j).prevY);
                        ammoList.get(i).updatePosition();
                        ammoList.get(j).updatePosition();
                        ammoList.get(i).rotation = Math.random()/5 - 0.1;
                        ammoList.get(j).rotation = Math.random()/5 - 0.1;
                        ammoList.get(i).updatePosition();
                        ammoList.get(j).updatePosition();
                    }
                }
            }
            if (collision(ammoList.get(i), ship) && ship.active){
                ammoCollision(i);
                i = ammoList.size();
            }
        }
    }
    
    public void respawnShip(){
        if(!ship.active && ship.counter > 100 && canWeSpawn() && ship.livesCounter > 0){
            ship.reset();
        }
    }
    
    public boolean canWeSpawn(){
        double x, y, h;
        for(int i = 0; i < asteroidList.size(); i++){
            x = asteroidList.get(i).xPosition - 450;
            y = asteroidList.get(i).yPosition - 300;
            h = Math.sqrt(x*x + y*y);
            if(h < 100){
                return false;
            }
        } 
        return true;
    }
    
    public void fire(){
        if(ship.counter > 50 && ship.active && bulletCounter > 0){
            bulletList.add(new Bullet(ship.drawShape.xpoints[0], ship.drawShape.ypoints[0], ship.angle));
            ship.counter = 0;
            laser.play();
            bulletCounter--;
        }
        if(bulletCounter < 0){
            bulletCounter = 0;
        }
    }
    
    public void checkAsteroidDestruction(){
        asteroidNum = (int) Math.round(Math.random() * 2) + 1;
        int asteroidLocation;
        for(int i = 0; i < asteroidList.size(); i++){            
            if(!asteroidList.get(i).active){
                if(asteroidList.get(i).size > 1){
                    for(int j = 0; j < asteroidNum; j++){
                        if(Math.random() < 0.5){
                            asteroidLocation = (int) Math.round(Math.random() * 10);
                        }
                        else{
                            asteroidLocation = (int) Math.round(Math.random() * -10);
                        }
                        asteroidList.add(new Asteroid(asteroidList.get(i).xPosition + asteroidLocation, asteroidList.get(i).yPosition + asteroidLocation, asteroidList.get(i).size - sizeDecrease));
                    }
                }            
                if(asteroidList.get(i).size == startingSize){
                    score += 20;
                }
                else if(asteroidList.get(i).size == startingSize - sizeDecrease){
                    score += 15;
                }
                else if(asteroidList.get(i).size == startingSize - sizeDecrease * 2){
                    score += 10;
                }
                else{
                    score += 5;
                }
                asteroidList.remove(i);
            }        
        }
    }
    
    public void restartGame(){
        ship.reset();
        ship.livesCounter = 3;
        score = 0;
        bulletCounter = bulletSet;
        initializeAsteroid();
        
        ammoSpawnCounter = ammoSpawnSet;
        gameOver = false;
    }
    
    public void startGame(){       
        ship = new SpaceCraft();
        bulletCounter = bulletSet;
        asteroidList = new ArrayList();
        initializeAsteroid();
        bulletList = new ArrayList();
        debrisList = new ArrayList();
        ammoList = new ArrayList();
        laser = getAudioClip(getCodeBase(), "laser80.wav");
        shipHit = getAudioClip(getCodeBase(), "explode1.wav");
        asteroidHit = getAudioClip(getCodeBase(), "explode0.wav");
        thruster = getAudioClip(getCodeBase(), "thruster.wav");
    }
    
    public void initializeAsteroid(){
        double x, y, h;
        asteroidNum = (int) Math.round(Math.random() * 10) + 5;
        for(int i = 0; i < asteroidNum; i++){
            asteroidList.add(new Asteroid(startingSize));
            for(int j = 0; j < asteroidList.size(); j++){
                if(i == j && j != asteroidList.size() - 1){ //to make sure that i and j are not equal
                    j++;// if they are, j is increased to next one
                }
                if(i != j){ // if the 2 asteroids being compared are not the same
                    x = asteroidList.get(i).xPosition - asteroidList.get(j).xPosition;
                    y = asteroidList.get(i).yPosition - asteroidList.get(j).yPosition;
                    h = Math.sqrt(x*x + y*y);
                    if(h < 150){
                        asteroidList.remove(i);// if asteroid spawns within radius it is removed
                        i--; // i is decreased to make sure that another asteroid is spawned instead of the one removed
                        System.out.println(i);
                    }
                }
            }
        } 
    }
    
    public void checkAddAmmo(){
        if (ammoSpawnCounter <= 0 && !gameOver){
            double x, y, h;
            ammoList.add(new Ammo());
            for(int i = 0; i < ammoList.size()-1; i++){
                x = ammoList.get(i).xPosition - ammoList.get(ammoList.size() - 1).xPosition;
                y = ammoList.get(i).yPosition - ammoList.get(ammoList.size() - 1).yPosition;
                h = Math.sqrt(x*x + y*y);
                if(h < 80){
                    ammoList.remove(ammoList.size()-1);
                    ammoSpawnCounter = 0;
                    break;
                }
            }
            for(int i = 0; i < asteroidList.size(); i++){
                x = asteroidList.get(i).xPosition - ammoList.get(ammoList.size() - 1).xPosition;
                y = asteroidList.get(i).yPosition - ammoList.get(ammoList.size() - 1).yPosition;
                h = Math.sqrt(x*x + y*y);
                if(h < 150){
                    ammoList.remove(ammoList.size()-1);
                    ammoSpawnCounter = 0;
                    System.out.println("works");
                    break;
                }
            }
            
            ammoSpawnCounter = ammoSpawnSet;
        }
        else{
            ammoSpawnCounter--;
        }
    }
    
    public void ammoCollision(int i){
        bulletCounter += ammoAmountSet;
        ammoList.remove(i);
    }
    
    public void bounce(VectorSprite a, VectorSprite b/*, double x1, double y1, double x2, double y2*/){
        a.rotation = 0;
        b.rotation = 0;
        if((a.xSpeed > 0 && b.xSpeed < 0) || (a.xSpeed < 0 && b.xSpeed > 0)){
            a.xSpeed *= -1;
            b.xSpeed *= -1;      
            //a.rotation *= -1;
            //b.rotation *= -1;
        }
        else{
            // xspeeds are the in the same direction
            if(Math.abs(a.xSpeed) > Math.abs(b.xSpeed)){
                a.xSpeed *= -1;
                //a.rotation *= -1;
            }
            else{
                b.xSpeed *= -1;
                //b.rotation *= -1;
            }
        }
        
        if((a.ySpeed > 0 && b.ySpeed < 0) || (a.ySpeed < 0 && b.ySpeed > 0)){
            a.ySpeed *= -1;
            b.ySpeed *= -1;       
            //a.rotation *= -1;
            //b.rotation *= -1;
        }
        else{
            // yspeeds are the in the same direction
            if(Math.abs(a.ySpeed) > Math.abs(b.ySpeed)){
                a.ySpeed *= -1;
                //a.rotation *= -1;
            }
            else{
                b.ySpeed *= -1;
                //b.rotation *= -1;
            }
        }
        /*a.xPosition = x1;
        a.yPosition = y1;
        b.xPosition = x2;
        b.yPosition = y2;*/
    }
    // TODO overwrite start(), stop() and destroy() methods
}

/* 
bouncing the asteroids away from each other
change asteroid shape

bounce method
*/