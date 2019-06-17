package sample.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class XCanvas extends Canvas implements Redrawable{

    static Image img;
    static {
        try {
            img = new Image(new FileInputStream("./src/sample/images/x.jpeg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    double destX;
    double destY;

    double widthBlock;
    double heightBlock;

    @Override
    public void setBlockSize(double widthBlock,double heightBlock){
        this.heightBlock=heightBlock;
        this.widthBlock=widthBlock;
    }

    public void markDestByMouse(double posX, double posY) {
        destX = (int)(posX / widthBlock);
        destY = (int)(posY / heightBlock);
    }

    @Override
    public void redraw() {
        double size=5;
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0,0,this.getWidth(),this.getHeight());
        gc.drawImage(img, destX * widthBlock - widthBlock * size*0.5, destY * heightBlock- heightBlock * size*0.5, widthBlock * size, heightBlock * size); // draw plane

    }

    public double getDestX() {
        return destX;
    }

    public double getDestY() {
        return destY;
    }
}
