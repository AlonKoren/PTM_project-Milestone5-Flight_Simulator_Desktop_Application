package sample.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class MapCanvas extends Canvas implements Redrawable {
    double[][] coordinates;
    double widthBlock;
    double heightBlock;
    double max;

    public void setCoordinates(double[][] coordinates) {
        this.coordinates = coordinates;
        this.setBlockSize(getWidth() / coordinates[0].length,getHeight() / coordinates.length);
        this.max = Arrays.stream(coordinates).flatMapToDouble(Arrays::stream).max().getAsDouble();
    }
    @Override
    public void setBlockSize(double widthBlock, double heightBlock) {
        this.heightBlock = heightBlock;
        this.widthBlock = widthBlock;
    }

    public double[][] getCoordinates() {
        return coordinates;
    }

    @Override
    public void redraw()
    {
        GraphicsContext gc = this.getGraphicsContext2D();

        gc.clearRect(0,0,this.getWidth(),this.getHeight());

        if(coordinates != null)
        {
            double red = 0,green = 0;
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


}
