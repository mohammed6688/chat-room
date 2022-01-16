import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatHandler extends Thread{
    //BufferedReader dis;
    DataInputStream dis;
    PrintStream ps;
    int index;
    static ArrayList<ChatHandler> arrayList=new ArrayList<ChatHandler>();

    public ChatHandler (Socket socket) throws IOException {
        //dis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        dis = new DataInputStream(socket.getInputStream());    //internal socket ear
        ps = new PrintStream(socket.getOutputStream());      //internal socket mouth
        arrayList.add(this);                    //adding the data in array to use it latter
        index=arrayList.size()-1;
        start();                                 //start the thread
    }

    @Override
    public void run() {
        try {
            while (true){
                //System.out.println(dis.readBoolean());
                    String message =dis.readLine();
                    sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                dis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ps.close();
            arrayList.remove(index);
        }
    }

    public void sendMessage(String message){
        for (ChatHandler obj :arrayList){
            obj.ps.println(obj.getId()+" "+message);
        }
    }

}
