package view;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MapDisplayer extends Canvas
{
    double[][] coordinates;
    double initX, initY;
    double distance;
    double width;
    double height;
    double widthBlock;
    double heightBlock;
    double max;
    int destX, destY; // position of plane dest
    double planeX, planeY; // position of plane.
//    String path;
//    double initMapPlaneX, initMapPlaneY;

    public boolean isMarkedOnMap; // == isDestOnMap
    public boolean isMapLoaded;
    public boolean isPathExists;
    public boolean isPlaneExists;



    public MapDisplayer()
    {
        isMarkedOnMap = false;
        isMapLoaded = false;
        isPathExists = false;
        isPlaneExists = true;
        //redraw();

    }

    public void setMapData(File fileCSV) throws FileNotFoundException{
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(fileCSV);
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        double x=Double.parseDouble(records.get(0).get(0));
        double y=Double.parseDouble(records.get(0).get(1));
        double distance=Double.parseDouble(records.get(1).get(0));
        List<List<String>> lists = records.subList(2, records.size());
        double[][] coordinates = lists.stream().map(strings -> strings.stream().mapToDouble(Double::parseDouble).toArray()).toArray(double[][]::new);
        setMapData(coordinates,x,y, distance);

    }
    private static final String COMMA_DELIMITER=",";
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
            return values;
        }
    }



    public void setMapData(double[][] coordinates,double x, double y, double distance)
    {

        isMapLoaded=true;
        this.coordinates = coordinates;
        this.initX=x;
        this.initY=y;
        this.distance = distance;
        this.max = max= Arrays.stream(coordinates).flatMapToDouble(Arrays::stream).max().getAsDouble();
        this.width = (double)(this.getWidth()) ;
        this.height = (double)(this.getHeight()) ;
        this.widthBlock = width / coordinates[0].length;
        this.heightBlock = height / coordinates.length;
    }

    private void reprint() {
        if(isMapLoaded)
            redraw();
        if (isMarkedOnMap)
            markDestByMouse(destX,destY);
        if (isPlaneExists)
            movePlane(planeX,planeY);
    }

    public void redraw()
    {
        if(isMapLoaded){
            isMapLoaded=false;
            reprint();
        }
        isMapLoaded=true;
        if(coordinates != null)
        {
            double red = 0,green = 0;
//            width = (double)(this.getWidth()) ;
//            height = (double)(this.getHeight()) ;
//            widthBlock = width / coordinates[0].length;
//            heightBlock = height / coordinates.length;
//            System.out.printf("width=%f, height=%f\n",width,height);
//            System.out.printf("widthBlock=%f, heightBlock=%f\n",widthBlock,heightBlock);
            GraphicsContext gc = getGraphicsContext2D();

            for(int i = 0; i < coordinates.length;i++) {
                for(int j = 0; j<coordinates[i].length; j++) {
                    if(coordinates[i][j] == (max * 0.5))
                    {
                        red = 255;
                        green = 255;
                    }
                    else if(coordinates[i][j] <= (max * 0.5))
                    {
                        red = 255;
                        green = coordinates[i][j] * (255 / max) * 2;
                    }
                    else
                    {
                        red = Math.abs(255 - ((coordinates[i][j] - (max/2)) * (255 / max) * 2));
                        green = 255;
                    }
//                    System.out.println("red="+red+", green="+green+",i="+i+",j="+j+",cor="+coordinates[i][j]);
                    gc.setFill(new Color(red/255, green/255, 0, 1));
                    gc.fillRect(j*widthBlock, i*heightBlock, widthBlock, heightBlock);
                    gc.fillText((int)coordinates[i][j] + "", j*widthBlock + 4, i*heightBlock + heightBlock - 4);
                }

            }
        }
    }

    public void movePlane(double posX, double posY) {
        if(isPlaneExists){
            isPlaneExists=false;
            reprint();
        }
        isPlaneExists=true;
        planeX = (int)(posX / widthBlock);
        planeY = (int)(posY / heightBlock);

        try {
            Image img = new Image(new FileInputStream("./src/sample/images/plane.png"));
            GraphicsContext gc = getGraphicsContext2D();
            //redraw(); // redraw the map
            gc.drawImage(img, planeX*widthBlock, planeY*heightBlock,widthBlock*10,heightBlock*10); // draw plane

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void markDestByMouse(double posX, double posY) {
        if(isMarkedOnMap){
            isMarkedOnMap=false;
            reprint();
        }
        isMarkedOnMap=true;
        destX = (int)(posX / widthBlock);
        destY = (int)(posY / heightBlock);

        // It's opposite from corX and corY because the (0,0) is top left
        // and the width means columns and height means rows.

        try {
            Image img = new Image(new FileInputStream("./src/sample/images/x.jpeg"));
            GraphicsContext gc = getGraphicsContext2D();
            //redraw(max); // redraw the map
            //movePlane(planeX, planeY); // redraw the plane location
            gc.drawImage(img, destX*widthBlock, destY*heightBlock); // draw the dest

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void markDestByPosition(int posX, int posY) {

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
