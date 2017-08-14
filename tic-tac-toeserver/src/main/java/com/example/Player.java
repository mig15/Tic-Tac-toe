package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Player {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private static boolean gameOver = false;
    private String figure;
    private String clientName;

    Player(Socket clientSocket) {
        this.clientSocket = clientSocket;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("IO streams create");
        } catch (IOException e) {
            System.out.println("ERROR:" + " IO streams not create");
        }
    }

    static boolean isGameOver() {
        return gameOver;
    }

    void setFigure(int figureCode) {
        if (figureCode == 0) {
            figure = "zero";
        } else if (figureCode == 1) {
            figure = "cross";
        }
    }

    void setClientName(String clientName) {
        this.clientName = clientName;
    }

    void runConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyClass.isMaximumPlayers()) {
                    out.println("server:opponent:true");
                    out.println("server:name:" + clientName);
                    out.println("server:figure:" + figure);
                }

                try {
                    while (!gameOver) {
                        String input = in.readLine();
                        switch (input) {
                            case "game over":
                                gameOver = true;
                                break;
                            default:
                                MyClass.addMSG(input);
                                break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("ERROR:" + " reading error");
                }

                closeConnection();
            }
        }).start();
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Connection close");
        } catch (IOException e) {
            System.out.println("ERROR:" + " connection not close");
        }
    }

    void sendMSG(String msg) {
        out.println(msg);
    }
}
