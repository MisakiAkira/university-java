/**
 * @author Daniliuk Andrei S24610
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatServer {

    private final String host;
    private final int port;
    private Thread thread;
    private final StringBuilder log = new StringBuilder();
    private ServerSocketChannel serverSocket;
    private Selector selector;
    private final Map<SocketChannel, String> socketId = new HashMap<>();

    public ChatServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startServer() {
        thread = new Thread(this::runServer);
        thread.start();
        System.out.println("Server started");
    }

    private void runServer() {
        try {
            initializeServerSocket();
            initializeSelector();

            while (serverSocket.isOpen()) {
                selector.select();

                for (Iterator<SelectionKey> iterator = selector.selectedKeys().iterator(); iterator.hasNext(); ) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    if (selectionKey.isAcceptable()) {
                        handleAccept();
                    } else if (selectionKey.isReadable()) {
                        handleRead(selectionKey);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeServerSocket() throws IOException {
        serverSocket = ServerSocketChannel.open();
        serverSocket.configureBlocking(false);
        serverSocket.socket().bind(new InetSocketAddress(host, port), 50);
    }

    private void initializeSelector() throws IOException {
        selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    }

    private void handleAccept() throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        send(login(client) + '\n');
        client.register(selector, SelectionKey.OP_READ);
    }

    private void handleRead(SelectionKey selectionKey) throws IOException {
        SocketChannel client = (SocketChannel) selectionKey.channel();
        StringBuilder stringBuilder = readFromChannel(client);

        for (String str : stringBuilder.toString().split("\0")) {
            if (stringBuilder.toString().equals("logged out")) {
                handleLogout(client);
            } else {
                log.append(LocalTime.now()).append(" ")
                        .append(socketId.get(client))
                        .append(": ").append(str).append("\n");
                send(str + '\n');
            }
        }
    }

    private StringBuilder readFromChannel(SocketChannel client) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder stringBuilder = new StringBuilder();

        while (true) {
            buffer.clear();
            int read = client.read(buffer);

            if (read == 0) {
                break;
            }

            if (read == -1) {
                client.close();
                break;
            }

            buffer.flip();
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes);
            stringBuilder.append(new String(bytes));
        }

        return stringBuilder;
    }

    private void handleLogout(SocketChannel client) throws IOException {
        String logoutMessage = socketId.get(client) + " logged out";
        log.append(LocalTime.now()).append(" ").append(logoutMessage).append("\n");
        client.close();
    }

    private String login(SocketChannel client) throws IOException {
        StringBuilder stringBuilder = readFromChannel(client);
        socketId.put(client, stringBuilder.toString());
        log.append(LocalTime.now()).append(" ").append(stringBuilder).append(" logged in").append("\n");
        return stringBuilder + "logged in";
    }

    public void stopServer() {
        thread.interrupt();
        closeServerSocket();
        closeSelector();
        System.out.println("Server stopped");
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeSelector() {
        try {
            if (selector != null)
                selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(String str) {
        try {
            for (SelectionKey key : selector.keys()) {
                if (key.isValid() && key.channel() instanceof SocketChannel) {
                    SocketChannel sch = (SocketChannel) key.channel();
                    sch.write(ByteBuffer.wrap(str.getBytes()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerLog() {
        return log.toString();
    }
}