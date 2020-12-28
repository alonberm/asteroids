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
public class Debris extends VectorSprite{
    public Debris(double x, double y){
        shape = new Polygon();
        drawShape = new Polygon();
        
        shape.addPoint(1, 1);
        shape.addPoint(-1, -1);
        shape.addPoint(-1, 1);
        shape.addPoint(1, -1);
        drawShape.addPoint(1, 1);
        drawShape.addPoint(-1, -1);
        drawShape.addPoint(-1, 1);
        drawShape.addPoint(1, -1);
        
        xPosition = x;
        yPosition = y;
        
        thrust = 5;
        double a = Math.random() * 2 * Math.PI;
        xSpeed = Math.cos(a) * thrust;
        ySpeed = Math.sin(a) * thrust;
        
    }
}
