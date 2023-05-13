/**
 * @author Daniliuk Andrei S24610
 */

package zad1;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChatClientTask implements Runnable {
    private final ChatClient client;
    private final List<String> messages;
    private final int wait;

    public static ChatClientTask create(ChatClient c, List<String> messages, int wait) {
        return new ChatClientTask(c, messages, wait);
    }

    public ChatClientTask(ChatClient client, List<String> messages, int wait) {
        this.client = client;
        this.messages = messages;
        this.wait = wait;
    }

    @Override
    public void run() {
        try {
            client.login();
            Thread.sleep(wait);
            client.receive();
            for (String message : messages) {
                client.send(message + "\0");
                client.receive();
                Thread.sleep(wait);
            }
            client.logout();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public ChatClient getClient() {
        return client;
    }

    public String get() throws InterruptedException, ExecutionException {
        Thread.sleep(1000);
        return client.getChatView();
    }
}