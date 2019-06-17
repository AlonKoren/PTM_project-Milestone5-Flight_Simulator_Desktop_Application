package sample.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PlaneCanvas extends Canvas implements Redrawable{

    static Image[] planes;

    static {
        planes=new Image[8];
        try {
            planes[0]=new Image(new FileInputStream("./src/sample/images/planes/plane-0.jpg"));
            planes[1]=new Image(new FileInputStream("./src/sample/images/planes/plane-45.png"));
            planes[2]=new Image(new FileInputStream("./src/sample/images/planes/plane-90.jpg"));
            planes[3]=new Image(new FileInputStream("./src/sample/images/planes/plane-135.png"));
            planes[4]=new Image(new FileInputStream("./src/sample/images/planes/plane-180.jpg"));
            planes[5]=new Image(new FileInputStream("./src/sample/images/planes/plane-225.png"));
            planes[6]=new Image(new FileInputStream("./src/sample/images/planes/plane-270.jpg"));
            planes[7]=new Image(new FileInputStream("./src/sample/images/planes/plane-315.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    double planeX;
    double planeY;

    double widthBlock;
    double heightBlock;

    Image currentPlane=planes[0];
    double headingDeg=0;

    @Override
    public void setBlockSize(double widthBlock,double heightBlock){
        this.heightBlock=heightBlock;
        this.widthBlock=widthBlock;
    }

    public void setPlaneCoordinate(double posX,double posY){
        planeX = (int)(posX / widthBlock);
        planeY = (int)(posY / heightBlock);
    }

    public double getPlaneX() {
        return planeX;
    }

    public double getPlaneY() {
        return planeY;
    }

    public void setHeading(double headingDeg){
        if(headingDeg>=0&&headingDeg<45)
            currentPlane=planes[0];
        if(headingDeg>=45&&headingDeg<90)
            currentPlane=planes[1];
        if(headingDeg>=90&&headingDeg<135)
            currentPlane=planes[2];
        if(headingDeg>=135&&headingDeg<180)
            currentPlane=planes[3];
        if(headingDeg>=180&&headingDeg<225)
            currentPlane=planes[4];
        if(headingDeg>=225&&headingDeg<270)
            currentPlane=planes[5];
        if(headingDeg>=270&&headingDeg<315)
            currentPlane=planes[6];
        if(headingDeg>=315)
            currentPlane=planes[7];
        this.headingDeg=headingDeg;
    }

    @Override
    public void redraw() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        gc.drawImage(currentPlane, planeX * widthBlock - widthBlock * 7.5, planeY * heightBlock- heightBlock * 7.5, widthBlock * 15, heightBlock * 15); // draw plane
    }
}
