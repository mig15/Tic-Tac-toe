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

    void setFigure(int figureCode) {
        if (figureCode == 0) {
            figure = "server:figure:zero";
        } else if (figureCode == 1) {
            figure = "server:figure:cross";
        }
    }

    void runConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyClass.isMaximumClients()) {
                    String pack = "server:opponent:true".concat("\n").concat(figure);
                    out.println(pack);
                }

                String input;
                try {
                    while ((input = in.readLine()) != null) {
                        if (input.equalsIgnoreCase("Game Over")) {
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
}
