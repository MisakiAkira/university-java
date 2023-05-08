package zad2;

public class StringTask implements Runnable{

    private final String srcWord;
    private String result;
    private final int times;
    private TaskState state;
    private Thread thread;

    StringTask(String src, int times){
        srcWord = src;
        this.times = times;
        state = TaskState.CREATED;
    }

    StringTask(StringTask str){
        srcWord = str.srcWord;
        this.times = str.times;
        state = TaskState.CREATED;
    }

    String getResult(){
        return result;
    }

    TaskState getState(){
        return state;
    }

    void start(){
        thread = new Thread(this);
        thread.start();
        state = TaskState.RUNNING;
    }

    void abort(){
        thread.interrupt();
        state = TaskState.ABORTED;
    }

    boolean isDone(){
        return state == TaskState.READY || state == TaskState.ABORTED;
    }

    @Override
    public void run() {
        String finalWord = "";
        int copyOfTimes = times;
        while (copyOfTimes-- > 0) {
            finalWord += srcWord;
            result = finalWord;
        }
        state = TaskState.READY;
    }
}

enum TaskState{
    CREATED, RUNNING, ABORTED, READY
}
