import java.io.*;
import java.net.*;

public class TestClient {
    public static void main(String args[]){
        try{
            Socket socket=new Socket("127.0.0.1", 5029);

            PrintWriter myprintWriter=new PrintWriter(socket.getOutputStream());
            BufferedReader myBufReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader systemInBufReader=new BufferedReader(new InputStreamReader(System.in));
            String lineFromSystemIn; 
            String lineFromServer;
            while (true){
                lineFromSystemIn = systemInBufReader.readLine();     
                myprintWriter.println(lineFromSystemIn);
                myprintWriter.flush();
                lineFromServer = myBufReader.readLine();
                if (null != lineFromServer){
                    System.out.println("Server Return Code :" + lineFromServer);
                    if ( !lineFromServer.equals("OK") ){
                        break;
                    }    
                }
                
            }
            myprintWriter.close();
            myBufReader.close();
            socket.close();
        } catch (Exception e){
            System.out.println("Error"+e);
        }
    }
}