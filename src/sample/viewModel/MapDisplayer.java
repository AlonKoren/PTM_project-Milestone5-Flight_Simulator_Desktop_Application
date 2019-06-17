package sample.viewModel;


import alon.flightsim.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MapDisplayer extends Pane
{
    @FXML
    Canvas mapCanvas;

    @FXML
    Canvas planeCanvas;

    @FXML
    Canvas xCanvas;

    @FXML
    Canvas pointCanvas;

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
    Client client;
//    String path;
//    double initMapPlaneX, initMapPlaneY;

    public boolean isMarkedOnMap; // == isDestOnMap
    public boolean isMapLoaded;
    public boolean isPathExists;
    public boolean isPlaneExists;
    public boolean isPointExists;



    public MapDisplayer()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../JavaFX Components/map_canvass.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        isMarkedOnMap = false;
        isMapLoaded = false;
        isPathExists = false;
        isPlaneExists = true;
        isPointExists=false;
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
        this.width = (double)(mapCanvas.getWidth()) ;
        this.height = (double)(mapCanvas.getHeight()) ;
        this.widthBlock = width / coordinates[0].length;
        this.heightBlock = height / coordinates.length;
    }



    public void redraw()
    {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();

        if(isMapLoaded){
            isMapLoaded=false;
            gc.clearRect(0,0,mapCanvas.getWidth(),mapCanvas.getHeight());
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

    public void showPoints(String movesSt)
    {
        GraphicsContext gc = pointCanvas.getGraphicsContext2D();
        if(isPointExists){
            isPointExists=false;
            gc.clearRect(0,0,planeCanvas.getWidth(),planeCanvas.getHeight());
        }
        isPointExists=true;
        try {
            Image img = new Image(new FileInputStream("./src/sample/images/point.png"));

            String[] moves = movesSt.split(",");
            int x=(int)planeX;
            int y=(int)planeY;
            for (int i = 0; i < moves.length; i++) {
                String move=moves[i];
                switch (move){
                    case "Up":
                    {
                        x--;
                        break;
                    }
                    case "Right":
                    {
                        y++;
                        break;
                    }
                    case "Down":
                    {
                        x++;
                        break;
                    }
                    case "Left":
                    {
                        y--;
                        break;
                    }
                }
                gc.drawImage(img, x*widthBlock, y*heightBlock,widthBlock*2,heightBlock*2);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void movePlane(double posX, double posY) {
        GraphicsContext gc = planeCanvas.getGraphicsContext2D();

        if(isPlaneExists){
            isPlaneExists=false;
            gc.clearRect(0,0,planeCanvas.getWidth(),planeCanvas.getHeight());
        }
        isPlaneExists=true;
        planeX = (int)(posX / widthBlock);
        planeY = (int)(posY / heightBlock);
//        System.out.println("plane:"+planeX+","+planeY);

        try {
            Image img = new Image(new FileInputStream("./src/sample/images/plane.png"));
            //redraw(); // redraw the map
            gc.drawImage(img, planeX*widthBlock, planeY*heightBlock,widthBlock*15,heightBlock*15); // draw plane
//            System.out.printf("print plane at %.2f,%.2f\n",planeX*widthBlock,planeY*heightBlock);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void markDestByMouse(double posX, double posY) {
        GraphicsContext gc = xCanvas.getGraphicsContext2D();

        if(isMarkedOnMap){
            isMarkedOnMap=false;
            gc.clearRect(0,0,xCanvas.getWidth(),xCanvas.getHeight());
        }
        isMarkedOnMap=true;
        destX = (int)(posX / widthBlock);
        destY = (int)(posY / heightBlock);
        System.out.println("x:"+destX+","+destY);

        // It's opposite from corX and corY because the (0,0) is top left
        // and the width means columns and height means rows.

        try {
            Image img = new Image(new FileInputStream("./src/sample/images/x.jpeg"));
            //redraw(max); // redraw the map
            //movePlane(planeX, planeY); // redraw the plane location
            gc.drawImage(img, destX*widthBlock, destY*heightBlock); // draw the dest

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    public void markDestByPosition(int posX, int posY) {
//
//        int corX = posX;
//        int corY = posY;
//
//        // It's opposite from corX and corY because the (0,0) is top left
//        // and the width means columns and height means rows.
//        destX = corX;
//        destY = corY;
//
//        try {
//            Image img = new Image(new FileInputStream("./resources/close.png"));
//            GraphicsContext gc = getGraphicsContext2D();
//            //redraw(max); // redraw the map
//            //movePlane(planeX, planeY); // redraw the plane location
//            gc.drawImage(img, corX*widthBlock, corY*heightBlock); // draw the dest
//
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    public void connectToServer(final Client client) {
        this.client=client;
        Timer myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Double lat = client.getValue("/position/latitude-deg");
                Double lon = client.getValue("/position/longitude-deg");

                double x =(((lat-initX)/distance));//lat=initX+x*distance;
                double y=(((lon-initY)/distance));//lon=initY+y*distance;

//                System.out.println("init:"+initX+","+initY);
//                System.out.println("pos:"+lat+","+lon);
//                System.out.println("distance:"+distance);
//                System.out.println("block:"+widthBlock+","+heightBlock);
//                System.out.println("result:"+x+","+y/1000*-1);
//                System.out.println("**************");

                movePlane(x/1000,y/1000*-1);
            }
        },1000,250);
    }

    public double[][] getCoordinates() {
        return coordinates;
    }
}
