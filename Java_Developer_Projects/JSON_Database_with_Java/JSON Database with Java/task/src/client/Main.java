package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class Main {

    private static InputArguments inputArguments;

    public static void main(String[] args) {
        initialise(args);
        runClient();
    }

    private static void initialise(String[] args) {
        inputArguments = new InputArguments(args);
        File file = new File(Constants.PATH_TO_DATA);
        file.getParentFile().mkdirs();
    }

    private static void runClient() {
        try (
                Socket socket = new Socket(InetAddress.getByName(Constants.ADDRESS), Constants.PORT);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        ) {
            System.out.println("Client started!");
            String message = inputArguments.parseIntoJson();
            if (Objects.equals(message, null)) {
                socket.close();
                return;
            }
            dataOutputStream.writeUTF(message);
            System.out.printf("Sent: %s\n", message);
            String input = dataInputStream.readUTF();
            System.out.printf("Received: %s\n", input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

