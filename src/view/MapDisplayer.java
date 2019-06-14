package view;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MapDisplayer extends Canvas
{
    double[][] coordinates;
    double x, y;
    double distance;
    double width;
    double height;
    double widthBlock;
    double heightBlock;
    double max;
    int destX, destY; // position of plane dest
    public double planeX, planeY; // position of plane.
    String path;
    double initMapPlaneX, initMapPlaneY;

    public boolean isMarkedOnMap; // == isDestOnMap
    public boolean isMapLoaded;
    public boolean isPathExists;
    public boolean isPlaneMoved;

    public MapDisplayer()
    {
        isMarkedOnMap = false;
        isMapLoaded = false;
        isPathExists = false;
        isPlaneMoved = true;
        //redraw();
    }

    public void setMapData(double[][] coordinates, double max, double x, double y, double distance)
    {
        this.coordinates = coordinates;
        this.planeX = x;
        this.planeY = y;
        this.initMapPlaneX = x;
        this.initMapPlaneY = y;
        this.distance = distance;
        this.max = max;
    }

    public void redraw(double max)
    {
        if(coordinates != null)
        {
            double red = 0,green = 0;
            width = (double)(this.getWidth()) ;
            height = (double)(this.getHeight()) ;
            widthBlock = width / coordinates[0].length;
            heightBlock = height / coordinates.length;

            GraphicsContext gc = getGraphicsContext2D();

            for(int i = 0; i < coordinates.length;i++) {
                for(int j = 0; j<coordinates[0].length; j++) {
                    if(coordinates[i][j] <= max * 0.5)
                    {
                        red = 255;
                        green = coordinates[i][j] * (255 / max) * 2;
                    }
                    else
                    {
                        red = Math.abs(255 - ((coordinates[i][j] - (max/2)) * (255 / max) * 2));
                        green = 255;
                    }

                    gc.setFill(new Color(red/255, green/255, 0, 1));
                    gc.fillRect(j*widthBlock, i*heightBlock, widthBlock, heightBlock);

                    gc.setFill(Color.BLACK);
                    gc.fillText((int)coordinates[i][j] + "", j*widthBlock + 4, i*heightBlock + heightBlock - 4);
                }

            }
        }
    }

    public void movePlane(double posX, double posY) {
        int corX = (int)(posX / widthBlock);
        int corY = (int)(posY / heightBlock);
        planeX = corX;
        planeY = corY;

        try {
            Image img = new Image(new FileInputStream("./resources/plane.png"));
            GraphicsContext gc = getGraphicsContext2D();
            //redraw(max); // redraw the map
            gc.drawImage(img, corX*widthBlock, corY*heightBlock); // draw plane

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void markDestByMouse(double posX, double posY) {
        int corX = (int)(posX / widthBlock);
        int corY = (int)(posY / heightBlock);

        // It's opposite from corX and corY because the (0,0) is top left
        // and the width means columns and height means rows.
        destX = corX;
        destY = corY;

        try {
            Image img = new Image(new FileInputStream("./resources/close.png"));
            GraphicsContext gc = getGraphicsContext2D();
            //redraw(max); // redraw the map
            //movePlane(planeX, planeY); // redraw the plane location
            gc.drawImage(img, corX*widthBlock, corY*heightBlock); // draw the dest

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void markDestByPosition(int posX, int posY) {
        isMarkedOnMap = true;
        int corX = posX;
        int corY = posY;

        // It's opposite from corX and corY because the (0,0) is top left
        // and the width means columns and height means rows.
        destX = corX;
        destY = corY;

        try {
            Image img = new Image(new FileInputStream("./resources/close.png"));
            GraphicsContext gc = getGraphicsContext2D();
            //redraw(max); // redraw the map
            //movePlane(planeX, planeY); // redraw the plane location
            gc.drawImage(img, corX*widthBlock, corY*heightBlock); // draw the dest

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
