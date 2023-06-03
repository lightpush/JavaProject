package org.example.excel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class EchoServer extends JFrame {
    private List<Socket> clientSockets;
    private List<Receiver> receivers;
    private ServerSocket listener;
    private JTextArea messageArea;

    public EchoServer() {
        setTitle("메뉴 주문 창");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        c.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        setSize(400, 200);
        setVisible(true);

        clientSockets = new ArrayList<>();
        receivers = new ArrayList<>();

        try {
            setupConnection();
        } catch (IOException e) {
            handleError(e.getMessage());
        }
    }

    private void setupConnection() throws IOException {
        listener = new ServerSocket(9999);
        while (true) {
            Socket socket = listener.accept();
            clientSockets.add(socket);

            Receiver receiver = new Receiver(socket);
            receivers.add(receiver);

            Thread thread = new Thread(receiver);
            thread.start();
        }
    }

    private void handleError(String errorMessage) {
        System.out.println(errorMessage);
        System.exit(1);
    }

    private class Receiver implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private BufferedWriter out;

        public Receiver(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e) {
                handleError("오류가 발생했습니다");
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    // 메뉴 주문을 서버 창과 messageArea에 출력
                    System.out.println("메뉴 주문: " + message);
                    final String finalMessage = message;
                    SwingUtilities.invokeLater(() -> {
                        EchoServer.this.messageArea.append("메뉴 주문: " + finalMessage + "\n");
                    });
                }
            } catch (IOException e) {
                handleError("오류가 발생했습니다");
            }
        }
    }

    public static void main(String[] args) {
        new EchoServer();
    }
}
