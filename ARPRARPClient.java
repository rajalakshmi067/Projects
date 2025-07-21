import java.io.*;
import java.net.*;
import java.util.*;

public class ARPRARPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        Scanner sc = new Scanner(System.in);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        while (true) {
            System.out.println("\n1. ARP Protocol");
            System.out.println("2. RARP Protocol");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();
            sc.nextLine(); // consume newline

            if (ch == 1) {
                dos.writeUTF("ARP");
                System.out.print("Enter IP Address: ");
                String ip = sc.nextLine();
                dos.writeUTF(ip);
                String mac = dis.readUTF();
                System.out.println("MAC Address: " + mac);
            } else if (ch == 2) {
                dos.writeUTF("RARP");
                System.out.print("Enter MAC Address: ");
                String mac = sc.nextLine();
                dos.writeUTF(mac);
                String ip = dis.readUTF();
                System.out.println("IP Address: " + ip);
            } else {
                dos.writeUTF("exit");
                break;
            }
        }

        dis.close();
        dos.close();
        socket.close();
        sc.close();
    }
}

