/**
 * @author Daniliuk Andrei S24610
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChatClient {
    private final String host;
    private final int port;
    private final String id;
    private final StringBuilder chat = new StringBuilder();
    private SocketChannel client;

    public ChatClient(String host, int port, String id) {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    public void login() {
        try {
            chat.append("=== ").append(id).append(" chat view\n").append(id).append(" logged in").append("\n");
            InetSocketAddress address = new InetSocketAddress(host, port);
            client = SocketChannel.open(address);
            client.configureBlocking(false);
            client.write(ByteBuffer.wrap(id.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout() {
        try {
            send("logged out");
            Thread.sleep(100);
            receive();
            chat.append(id).append(" logged out").append("\n");
            client.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String req) {
        try {
            client.write(ByteBuffer.wrap(req.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder stringBuilder = new StringBuilder();
            buffer.clear();
            int length;

            while ((length = client.read(buffer)) > 0) {
                buffer.flip();
                byte[] bytes = new byte[length];
                buffer.get(bytes);
                stringBuilder.append(new String(bytes));
                buffer.clear();
            }

            if (length == -1) {
                client.close();
            }

            chat.append(stringBuilder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getChatView() {
        return chat.toString();
    }
}