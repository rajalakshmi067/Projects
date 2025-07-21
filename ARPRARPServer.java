import java.io.*;
import java.net.*;
import java.util.*;

public class ARPRARPServer {
    static Map<String, String> arpTable = new HashMap<>(); // IP -> MAC

    public static void main(String[] args) throws IOException {
        // Populate a sample ARP table
        arpTable.put("192.168.20.1", "18:C2:41:32:A5:EB");
        arpTable.put("192.168.20.255", "18:C2:41:32:A5:EB");

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started... waiting for client");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

        while (true) {
            String choice = dis.readUTF();
            if (choice.equalsIgnoreCase("exit")) break;

            switch (choice) {
                case "ARP":
                    String ip = dis.readUTF();
                    String mac = arpTable.getOrDefault(ip, "MAC Not Found");
                    dos.writeUTF(mac);
                    break;

                case "RARP":
                    String searchMac = dis.readUTF();
                    String foundIP = "IP Not Found";
                    for (Map.Entry<String, String> entry : arpTable.entrySet()) {
                        if (entry.getValue().equalsIgnoreCase(searchMac)) {
                            foundIP = entry.getKey();
                            break;
                        }
                    }
                    dos.writeUTF(foundIP);
                    break;

                default:
                    dos.writeUTF("Invalid Option");
                    break;
            }
        }

        dis.close();
        dos.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Server terminated.");
    }
}

