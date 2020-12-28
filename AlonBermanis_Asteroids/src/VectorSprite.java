/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
/**
 *
 * @author RealProgramming4Kids
 */
public class VectorSprite {
    double xPosition, yPosition, xSpeed, ySpeed, angle, rotation, thrust;
    Polygon shape, drawShape;
    boolean active;
    int counter;
    
    public void paint(Graphics g){
        g.drawPolygon(drawShape);
    }
    
    public void updatePosition(){
        counter++;
        xPosition += xSpeed;
        yPosition += ySpeed;
        wrapAround();
        int x, y;
        for(int i = 0; i < shape.npoints; i++){
            //shape.xpoints[i] += xSpeed;
            //shape.ypoints[i] += ySpeed;
            x = (int) Math.round(shape.xpoints[i] * Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
            y = (int) Math.round(shape.xpoints[i] * Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));
            //x = (int) Math.round(shape.xpoints[i] * Math.cos(angle));
            //y = (int) Math.round(shape.xpoints[i] * Math.sin(angle));
            drawShape.xpoints[i] = x;
            drawShape.ypoints[i] = y;
        }
        drawShape.invalidate();
        drawShape.translate((int)Math.round(xPosition), (int)Math.round(yPosition));
    }
    
    private void wrapAround(){
        if (xPosition > 900){
            xPosition = 0;
        }
        
        if (xPosition < 0){
            xPosition = 900;
        }
        
        if (yPosition > 600){
            yPosition = 0;
        }
        
        if (yPosition < 0){
            yPosition = 600;
        }
    }
}
