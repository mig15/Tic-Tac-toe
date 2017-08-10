package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Client {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private String figure;

    Client(Socket clientSocket) {
        this.clientSocket = clientSocket;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("IO streams create");
        } catch (IOException e) {
            System.out.println("ERROR:" + " IO streams not create");
        }
    }

    void setFigure(String figure) {
        this.figure = figure;
    }

    void runConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyClass.isMaximumClients()) {
                    System.out.println("Clients list is full");
                    StringBuilder sb = new StringBuilder();
                    sb.append("opponent:true").append("\n").append(figure);
                    out.println(sb);
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

    private void f1() {
        String input = "";
        while (true) {
            try {
                input = in.readLine();
            } catch (IOException e) {
                System.out.println("ERROR:" + " reading error");
            }

            if (input.equalsIgnoreCase("Game Over")) {
                break;
            }
        }
    }
}
