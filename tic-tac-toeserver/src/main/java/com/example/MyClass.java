package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyClass {

    private static final int MAXIMUM_CLIENTS = 2;

    private static ServerSocket serverSocket;

    private static List<Client> clientList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(9954);
            System.out.println("ServerSocket create");
        } catch (IOException e) {
            System.out.println("ERROR:" + " serverSocket not create");
        }


        while (clientList.size() < MAXIMUM_CLIENTS) {
            Client client;
            try {
                System.out.println("Waiting client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connect");
                client = new Client(clientSocket);
                clientList.add(client);
            } catch (IOException e) {
                System.out.println("ERROR:" + " client not connect");
            }
        }

        defineFigures();
    }

    private static void defineFigures() {
        Random random = new Random();
        int figureCode = random.nextInt(MAXIMUM_CLIENTS);

        //TODO

        for (int i = 0; i < MAXIMUM_CLIENTS; i++) {
            if (i == 0 && figureCode == 0) {
                String figure = "figure:zero";
                clientList.get(i).setFigure(figure);
            }
        }
    }

    static boolean isMaximumClients() {
        return clientList.size() == MAXIMUM_CLIENTS;
    }
}
