/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Polygon;
/**
 *
 * @author RealProgramming4Kids
 */
public class SpaceCraft extends VectorSprite{
    int livesCounter;
    public SpaceCraft(){//constructor
        shape = new Polygon();
        drawShape = new Polygon();
        
        shape.addPoint(25, 0);
        shape.addPoint(-10, 15);
        shape.addPoint(-10, -15);
        drawShape.addPoint(25, 0);
        drawShape.addPoint(-10, 15);
        drawShape.addPoint(-10, -15);
       
        xPosition = 450;
        yPosition = 300;
        
        rotation = 0.2;
        thrust = 0.1;
        
        active = true;
        livesCounter = 3;
    }
    
    public void accelarate(){
        if (xSpeed < 4){
            xSpeed += Math.cos(angle) * thrust;
            ySpeed += Math.sin(angle) * thrust;
        }
    }
    
    public void stop(){
        xSpeed = 0;
        ySpeed = 0;
    }
    
    public void rotateLeft(){
        angle -= rotation;
    }
    
    public void rotateRight() {
        angle += rotation;
    }
    
    public void hit(){
        active = false;
        counter = 0;
        livesCounter -= 1;
    }
    
    public void reset(){
        xSpeed = 0;
        ySpeed = 0;
        xPosition = 450;
        yPosition = 300;
        angle = 0;
        active = true;
    }
}
