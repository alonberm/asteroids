
import java.awt.Polygon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RealProgramming4Kids
 */
public class Bullet extends VectorSprite{
    public Bullet(double x, double y, double a){
        shape = new Polygon();
        drawShape = new Polygon();
        
        shape.addPoint(0,0);
        shape.addPoint(0,0);
        shape.addPoint(0,0);
        shape.addPoint(0,0);
        drawShape.addPoint(0,0);
        drawShape.addPoint(0,0);
        drawShape.addPoint(0,0);
        drawShape.addPoint(0,0);
        
        thrust = 10;
        xPosition = x;
        yPosition = y;
        angle = a;
        
        xSpeed = Math.cos(a) * thrust;
        ySpeed = Math.sin(a) * thrust;
        
        active = true;
        
    }
}
