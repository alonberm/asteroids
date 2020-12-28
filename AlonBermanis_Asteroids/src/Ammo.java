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
public class Ammo extends VectorSprite{
    public Ammo(){
        shape = new Polygon();
        drawShape = new Polygon();
            
        shape.addPoint(10, -10);
        shape.addPoint(10, 10);
        shape.addPoint(-10, 10);
        shape.addPoint(-10, -10);
        drawShape.addPoint(10, -10);
        drawShape.addPoint(10, 10);
        drawShape.addPoint(-10, 10);
        drawShape.addPoint(-10, -10);
        
        xPosition = Math.random() * 900;
        yPosition = Math.random() * 600;
        
        double h, a;
        h = Math.random() + 1;
        a = Math.random() * Math.PI * 2;
        xSpeed = Math.cos(a) * h;
        ySpeed = Math.sin(a) * h;
        rotation = Math.random()/5 - 0.1;
        
        active = true;
    }
    
    public void updatePosition(){
        angle += rotation;
        super.updatePosition(); //use VectorSprite updatePosition()
    }
}
