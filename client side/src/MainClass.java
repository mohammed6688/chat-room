import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainClass extends Application {
    TextArea textArea;
    Label label;
    TextField textField;
    Button button;
    BorderPane borderPane;
    DataInputStream dis;
    PrintStream ps;
    Socket socket;
    Thread thread;

    @Override
    public void init() throws Exception {
        DrawUi();
        initializeServer();

    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        button.addEventHandler(ActionEvent.ACTION, event -> {
            if (!textField.getText().equals("")){
               // ps.write(1);
                ps.println(textField.getText());
                textField.setText("");
            }
        });

        thread=new Thread(() -> {
            while (true){
                try {
                    String message=dis.readLine();
                    textArea.appendText("\n"+message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        Scene scene = new Scene(borderPane,600,400);
        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private void DrawUi() {
        textArea = new TextArea("Chat Message:");
        label=new Label("Enter your message");
        textField = new TextField();
        textField.setPromptText("Enter your message");

        button = new Button("Send");
        button.setDefaultButton(true);
        textArea.setEditable(false);

        FlowPane flowPane = new FlowPane(label,textField,button);
        flowPane.setOrientation(Orientation.HORIZONTAL);
        borderPane = new BorderPane();
        borderPane.setCenter(textArea);
        borderPane.setBottom(flowPane);
    }
    private void initializeServer() throws IOException {
        socket = new Socket("127.0.0.1",5005);   //create object of the server socket
        dis=new DataInputStream(socket.getInputStream());       //mouth of server socket (listen from it)
        ps=new PrintStream(socket.getOutputStream());           //ear of server socket  (write to it)
    }
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void stop() throws Exception {
        thread.stop();
        ps.print(false);
        ps.close();
        dis.close();
        socket.close();
    }
}
