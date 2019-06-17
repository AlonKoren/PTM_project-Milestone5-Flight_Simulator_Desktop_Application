package sample.Model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PTMClient{

    Socket socket=null;
    PrintWriter out=null;
    BufferedReader in=null;

    public PTMClient(String ip, int port) throws IOException {
        System.out.println(ip+":"+port);
        socket=new Socket(ip,port);
        socket.setSoTimeout(3000);
        out=new PrintWriter(socket.getOutputStream());
        in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String sendMatrix(double[][] matrix,int startX,int startY,int endX,int endY) throws IOException {
//        System.out.println("\tsending problem...");
        int i,j;
        for(i=0;i<matrix.length;i++){
//            System.out.print("\t");
            for(j=0;j<matrix[i].length-1;j++){
                out.print(matrix[i][j]+",");
//                System.out.print(matrix[i][j]+",");
            }
            out.println(matrix[i][j]);
//            System.out.println(matrix[i][j]);
        }
        out.println("end");
        System.out.println("start:"+startX+","+startY);
        out.println(startX+","+startY);
        System.out.println("end:"+endX+","+endY);
        out.println(endX+","+endY);
        out.flush();

        String ans=in.readLine();
        return ans;
    }


    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
