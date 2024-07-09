package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

    private static InputArguments inputArguments;

    private static volatile boolean exitFlag = false;

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static ServerSocket serverSocket;

    static InputArguments getInputArguments() {
        return inputArguments;
    }

    public static Lock getWriteLock() {
        return lock.writeLock();
    }

    public static Lock getReadLock() {
        return lock.readLock();
    }

    public static void main(String[] args) {
        initialise(args);
        runServer();
    }

    private static void initialise(String[] args) {
        inputArguments = new InputArguments(args);
        File file = new File(Constants.PATH_TO_DATA);
        try {
            if(file.getParentFile().mkdirs()) {
                Files.write(Path.of(Constants.PATH_TO_DATA), new byte[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runServer() {
        try {
            serverSocket = new ServerSocket(Constants.PORT, Constants.BACKLOG, InetAddress.getByName(Constants.ADDRESS));
            System.out.println("Server started!");
            ExecutorService executor = Executors.newFixedThreadPool(Constants.POOL_SIZE);

            while (!exitFlag) {
                try {
                    Socket socket = serverSocket.accept();
                    executor.submit(new handleSocket(socket));
                } catch (Exception e) {
                    if (exitFlag) {
                        break;
                    } else {
                        e.printStackTrace();
                    }
                }
            }
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error occurred while closing the server socket: " + e.getMessage());
            }
        }
    }

    public static void shutdownServer() {
        exitFlag = true;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error occurred while closing the server socket: " + e.getMessage());
        }
    }
}