import java.io.*;
import java.net.*;
import java.applet.Applet;

public class TestServer{
    public static void main(String args[]) {
        try {
            ServerSocket server = null;
            try {
                server = new ServerSocket(5029);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            Socket socket = null;
            try {
                socket=server.accept();
            } catch(Exception e) {
                e.printStackTrace();
            }

            BufferedReader myBufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter myprintWriter = new PrintWriter(socket.getOutputStream());
            
            BufferedReader systemInReader = new BufferedReader(new InputStreamReader(System.in));
            
            String thisLine;
            String returnMsgToClient;
            while (true) {
                /* get the msg from client */
                thisLine = myBufReader.readLine();
                if (null != thisLine){  
                    System.out.println("Client : " + thisLine);
                    int batReturnCode = runBat(thisLine);
                    if (thisLine.equals("###")){
                        /* halt the server */
                        returnMsgToClient = "ERROR";
                        myprintWriter.println(returnMsgToClient);
                        myprintWriter.flush(); 
                        break;
                    } else {
                        returnMsgToClient = "OK";
                        //For Debug
                        System.out.println("returnMsgToClient : " + batReturnCode);
                        /* return the value of client */
                        myprintWriter.println(batReturnCode);
                        myprintWriter.flush(); 
                    }
                }
            }

            myprintWriter.close();
            myBufReader.close();
            socket.close();
            server.close();
            
        } catch(Exception e) {
                e.printStackTrace();
        }
    }
    
    public static int runBat(String strCmd){
        /* 获取当前应用程序的Runtime */
        Runtime currentRunTime = Runtime.getRuntime();
        Process currentProcess = null;
        try {
            currentProcess = currentRunTime.exec(strCmd);
            currentProcess.waitFor();
            
        } catch(IOException e){
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        
        return currentProcess.exitValue();
    }
    
}


