package sample.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PointCanvas extends Canvas implements Redrawable{

    int startX;
    int startY;
    double widthBlock;
    double heightBlock;
    String[] moves=null;
    public void showPoints(String movesSt,int startX,int startY)
    {
//        int x=(int)planeCanvas.getPlaneX();
//        int y=(int)planeCanvas.getPlaneY();
        moves = movesSt.split(",");
        this.startX=startX;
        this.startY=startY;
    }

    @Override
    public void setBlockSize(double widthBlock,double heightBlock){
        this.heightBlock=heightBlock;
        this.widthBlock=widthBlock;
    }


    @Override
    public void redraw() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0,0,this.getWidth(),this.getHeight());
        if (moves!=null)
        {
            double x = startX;
            double y = startY;
            for (int i = 1; i < moves.length; i++)
            {
                String move = moves[i];
                gc.setStroke(Color.BLACK);
                switch (move) {
                    case "Up": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, (x - 1) * widthBlock, y * heightBlock);
                        x--;
                        break;
                    }
                    case "Right": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, x * widthBlock, (y + 1) * heightBlock);
                        y++;
                        break;
                    }
                    case "Down": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, (x + 1) * widthBlock, y * heightBlock);
                        x++;
                        break;
                    }
                    case "Left": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, x * widthBlock, (y - 1) * heightBlock);
                        y--;
                        break;
                    }
                }
            }
        }
    }
}
