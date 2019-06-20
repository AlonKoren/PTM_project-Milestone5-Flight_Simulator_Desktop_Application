package sample.view;

import Matrices.Index;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PointCanvas extends Canvas implements Redrawable{

    int startX;
    int startY;
    double widthBlock;
    double heightBlock;
    String[] moves=null;
    LinkedList<Index> indexs=new LinkedList<>();


    public void showPoints(String movesSt,int startX,int startY)
    {
//        int x=(int)planeCanvas.getPlaneX();
//        int y=(int)planeCanvas.getPlaneY();
        moves = movesSt.split(",");
        this.startX=startX;
        this.startY=startY;

        indexs.clear();

        double x = startX;
        double y = startY;
        System.out.println("moves:"+moves.length+"="+Arrays.toString(moves));

        for (int i = 0; i < moves.length; i++) {

            /**
             * TODO check if needed y negative or positive
             */

            indexs.add(new Index((int) x, (int) y * -1));
            String move = moves[i];
            switch (move) {
                case "Left": {
                    x--;
                    break;
                }
                case "Down": {
                    y++;
                    break;
                }
                case "Right": {
                    x++;
                    break;
                }
                case "Up": {
                    y--;
                    break;
                }
            }
        }
        indexs.add(new Index((int) x, (int) y * -1));
//        System.out.println("indexs:"+indexs.size()+"="+indexs.toString());
//        for (int i = 1; i < indexs.size(); i++) {
//            double v = Math.toDegrees(Math.atan2(indexs.get(i).column - indexs.get(i - 1).column, indexs.get(i).row - indexs.get(i - 1).row));
//            if (v<0) v+=360;
////                System.out.println(v);
//        }

    }

    public LinkedList<Index> getIndexs() {
        return indexs;
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
//            System.out.println("moves:"+moves.length+"="+Arrays.toString(moves));

            for (int i = 0; i < moves.length; i++)
            {
                String move = moves[i];
                gc.setStroke(Color.BLACK);
                switch (move) {
                    case "Left": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, (x - 1) * widthBlock, y * heightBlock);
                        x--;
                        break;
                    }
                    case "Down": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, x * widthBlock, (y + 1) * heightBlock);
                        y++;
                        break;
                    }
                    case "Right": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, (x + 1) * widthBlock, y * heightBlock);
                        x++;
                        break;
                    }
                    case "Up": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, x * widthBlock, (y - 1) * heightBlock);
                        y--;
                        break;
                    }
                }
            }
        }
    }
}
