package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyClass {

    private static final int MAXIMUM_PLAYERS = 2;

    private static ServerSocket serverSocket;

    private static List<Player> playerList = new ArrayList<>();
    private static volatile List<String> messages = new ArrayList<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(9954);
            System.out.println("ServerSocket create");
        } catch (IOException e) {
            System.out.println("ERROR:" + " serverSocket not create");
        }

        int clientCount = 0;
        while (playerList.size() < MAXIMUM_PLAYERS) {
            try {
                System.out.println("Waiting client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Player connect");
                Player player = new Player(clientSocket);
                clientCount++;
                player.setClientName("player" + Integer.toString(clientCount));
                playerList.add(player);
            } catch (IOException e) {
                System.out.println("ERROR:" + " client not connect");
            }
        }

        defineFigures();
        playerList.get(0).runConnection();
        playerList.get(1).runConnection();
        f();
    }

    private static void defineFigures() {
        Random random = new Random();
        int figurePlayer1 = random.nextInt(MAXIMUM_PLAYERS);
        int figurePlayer2 = 0;
        if (figurePlayer1 == 0) {
            figurePlayer2 = 1;
        }

        for (int i = 0; i < MAXIMUM_PLAYERS; i++) {
            if (i == 0) {
                playerList.get(i).setFigure(figurePlayer1);
            } else if (i == 1) {
                playerList.get(i).setFigure(figurePlayer2);
            }
        }
    }

    static boolean isMaximumPlayers() {
        return playerList.size() == MAXIMUM_PLAYERS;
    }

    static void addMSG(String msg) {
        messages.add(msg);
    }

    private static void f() {
        while (!Player.isGameOver()) {
            if (messages.size() > 0) {
                String str = messages.get(0);
                int index = str.indexOf(":");

                if (str.substring(0, index).equals("player1")) {
                    playerList.get(1).sendMSG(str);
                } else if (str.substring(0, index).equals("player2")) {
                    playerList.get(0).sendMSG(str);
                }

                messages.remove(0);
            }
        }
    }
}
