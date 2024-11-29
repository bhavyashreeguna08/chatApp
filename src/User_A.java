import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class User_A extends Frame implements Runnable, ActionListener {

    TextField textField;

    TextArea textArea;

    Button send;

    Socket socket;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    Thread chat;

    User_A(){

        textField = new TextField();
        textArea = new TextArea();
        send = new Button("Send");

        send.addActionListener(this);

        try {
            socket = new Socket("localhost", 12000 );

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception E){

        }

        add(textField);
        add(textArea);
        add(send);

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();

        setSize(500, 500);
        setTitle("User A");
        setLayout(new FlowLayout());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String msg = textField.getText();
        textArea.append("User A: "+msg+"\n");
        textField.setText("");

        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException ex) {

        }
    }

    public static void main(String[] args) {

        new User_A();
    }

    public void run(){
        while(true){
            try{
                String msg = dataInputStream.readUTF();
                textArea.append("User B: " +msg+ "\n");
            } catch (Exception e) {

            }
        }
    }
}
