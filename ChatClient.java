import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatClient extends JFrame {
    private String name;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatClient(String name) {
        super("Chat Client - " + name);
        this.name = name;

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        sendButton = new JButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
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

    public void connect(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            appendToChatArea("Conectado ao servidor na porta " + port);

            Thread receivingThread = new Thread(new MessageReceiver());
            receivingThread.start();
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(this, "Servidor desconhecido: " + serverAddress, "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Não foi possível conectar ao servidor: " + serverAddress + " na porta " + port, "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void disconnect() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            appendToChatArea("[CLIENTE]: " + message); 
            output.println("[CLIENTE]: " + message);
            inputField.setText("");
        }
    }

    private void appendToChatArea(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chatArea.append(message + "\n");
            }
        });
    }

    private class MessageReceiver implements Runnable {
        public void run() {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    appendToChatArea(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }
    }

    public static void main(String[] args) {
        String clientName = "Cliente";
        String serverAddress = "localhost";
        int port = 1234;

        ChatClient client = new ChatClient(clientName);
        client.setVisible(true);
        client.connect(serverAddress, port);
    }
}
