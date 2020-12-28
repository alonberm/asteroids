
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
public class Asteroid extends VectorSprite{
    float size;
    //double prevX, prevY;
    public Asteroid(float s){
        size = s;
        initializeAsteroid();
    }
    
    public Asteroid(double x, double y, float s){
        size = s;
        initializeAsteroid();
        xPosition = x;
        yPosition = y;

    }
       
    public void initializeAsteroid(){//constructor
        shape = new Polygon();
        drawShape = new Polygon();
        
        shape.addPoint(30, 3);
        shape.addPoint(5, 35);
        shape.addPoint(-25, 10);
        shape.addPoint(-17, -15);
        shape.addPoint(5, -20);
        drawShape.addPoint(30, 3);
        drawShape.addPoint(5, 35);
        drawShape.addPoint(-25, 10);
        drawShape.addPoint(-17, -15);
        drawShape.addPoint(5, -20);     
        
        for(int i = 0; i < shape.npoints; i++){
            shape.xpoints[i] = (int) Math.round(shape.xpoints[i] * size);
            shape.ypoints[i] = (int) Math.round(shape.ypoints[i] * size);
            drawShape.xpoints[i] = (int) Math.round(drawShape.xpoints[i] * size);
            drawShape.ypoints[i] = (int) Math.round(drawShape.ypoints[i] * size);
        }
        
        active = true;
        
        double h, a;
        h = Math.random() + 1;
        a = Math.random() * Math.PI * 2;
        xSpeed = Math.cos(a) * h;
        ySpeed = Math.sin(a) * h;
        
        h = Math.random() * 400 + 100;
        a = Math.random() * Math.PI * 2;
        xPosition = Math.cos(a) * h + 450;
        yPosition = Math.sin(a) * h + 300;
        
        rotation = Math.random()/5 - 0.10;
        //prevX = xPosition;
        //prevY = yPosition;
    }
    
 
    
    public void updatePosition(){
        angle += rotation;
        super.updatePosition(); //use VectorSprite updatePosition()
    }
}
