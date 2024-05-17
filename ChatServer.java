import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatServer extends JFrame {
    private List<ClientHandler> clients = new ArrayList<>();
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatServer() {
        super("Chat Server");

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendServerMessage();
            }
        });

        sendButton = new JButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendServerMessage();
            }
        });

        JScrollPane scrollPane = new JScrollPane(chatArea);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void sendServerMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            broadcastMessage("[SERVIDOR] " + message, null);
            inputField.setText("");
        }
    }

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            appendToChatArea("Servidor iniciado na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                appendToChatArea("Novo cliente conectado");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
        appendToChatArea(message);
    }

    private void appendToChatArea(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chatArea.append(message + "\n");
            }
        });
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.setVisible(true);
        server.start(1234);
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new PrintWriter(clientSocket.getOutputStream(), true);

                String message;
                while ((message = input.readLine()) != null) {
                    broadcastMessage(message, this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                    output.close();
                    clientSocket.close();
                    clients.remove(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            output.println(message); 
        }
    }
}
