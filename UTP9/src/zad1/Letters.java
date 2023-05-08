package zad1;

import java.util.ArrayList;
import java.util.List;

public class Letters {

    private final List<Thread> threads = new ArrayList<>();

    Letters(String src){
        for (int i = 0; i < src.length(); i++) {
            int finalI = i;
            Runnable newRunnable = () -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print(src.charAt(finalI));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                        break;
                    }
                }
            };
            threads.add(new Thread(newRunnable, "Thread " + src.charAt(i)));
        }
    }

    public List<Thread> getThreads(){
        return threads;
    }

}
